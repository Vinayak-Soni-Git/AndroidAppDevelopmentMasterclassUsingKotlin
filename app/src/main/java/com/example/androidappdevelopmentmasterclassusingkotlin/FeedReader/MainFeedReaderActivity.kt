package com.example.androidappdevelopmentmasterclassusingkotlin.FeedReader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidappdevelopmentmasterclassusingkotlin.R
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageUrl: String = ""

    override fun toString(): String {
        return """
            name = $name
            artist = $artist
            releaseDate = $releaseDate
            imageUrl = $imageUrl
        """.trimIndent()
    }
}

class MainFeedReaderActivity : AppCompatActivity() {
    private lateinit var xmlListView: ListView

    //    private val downloadData by lazy { DownloadData(this, xmlListView) }
    private var downloadData: DownloadData? = null

    private var feedUrl: String =
        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplicatoins/limit=10/xml"
    private var feedLimit = 10

    private var feedCachedUrl = "INVALIDATED"
    private val STATE_URL = "feedUrl"
    private val STATE_LIMIT = "feedLimit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_feed_reader)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString(STATE_URL).toString()
            feedLimit = savedInstanceState.getInt(STATE_LIMIT)

        }

        xmlListView = findViewById(R.id.top10_list_view)
        downloadUrl(feedUrl.format(feedLimit))
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) :
            AsyncTask<String, Void, String>() {

            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            @Deprecated("Deprecated in Java")
            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val parseApplications = ParseApplications()
                parseApplications.parse(result)

//                val arrayAdapter = ArrayAdapter<FeedEntry>(
//                    propContext,
//                    R.layout.list_top_10_item,
//                    parseApplications.applications
//                )
//                propListView.adapter = arrayAdapter

                val feedAdapter =
                    FeedAdapter(propContext, R.layout.list_record, parseApplications.applications)
                propListView.adapter = feedAdapter
            }

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg url: String?): String {
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e("MAIN_FEED_READER_ACTIVITY", "error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()
            }

//            private fun downloadXML(urlPath: String?): String {
//                val xmlResult = StringBuilder()
//                try {
//                    val url = URL(urlPath)
//                    val connection: HttpURLConnection = url.openConnection() as HttpsURLConnection
//                    val response = connection.responseCode
//                    Log.d("TAG", "downloadXML: the response code was $response")
//
////            val inputStream = connection.getInputStream()
////            val inputStreamReader = InputStreamReader(inputStream)
////            val reader = BufferedReader(inputStreamReader)
////                    val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
////                    val inputBuffer = CharArray(500)
////                    var charsRead = 0
////                    while (charsRead >= 0) {
////                        charsRead = reader.read(inputBuffer)
////                        if (charsRead > 0) {
////                            xmlResult.append(String(inputBuffer, 0, charsRead))
////                        }
////                    }
////                    reader.close()
//
////                    val stream = connection.getInputStream()
//                    connection.getInputStream().buffered().reader()
//                        .use { xmlResult.append(it.readText()) }
//
//                    Log.d("TAG", "Received ${xmlResult.length} bytes")
//                    return xmlResult.toString()
////                } catch (e: MalformedURLException) {
////                    Log.e("TAG", "downloadXML: Invalid URL ${e.message}")
////                } catch (e: IOException) {
////                    Log.e("TAG", "downloadXML: IO Exception reading data: ${e.message}")
////                } catch (e: SecurityException) {
////                    Log.e("TAG", "downloadXML: Security Exception ${e.message}")
////                } catch (e: Exception) {
////                    Log.e("TAG", "Unknown error: ${e.message}")
////                }
//                } catch (e: Exception) {
//                    val errorMessage: String = when (e) {
//                        is MalformedURLException -> "downloadXML: Invalid URL ${e.message}"
//
//                        is IOException -> "downloadXML: IO Exception reading data: ${e.message}"
//
//                        is SecurityException -> {
//                            e.printStackTrace()
//                            "downloadXML: Security Exception: Needs permission? ${e.message}"
//                        }
//
//                        else -> "Unknown error: ${e.message}"
//                    }
//                }
//                return ""
//            }
        }
    }

    private fun downloadUrl(feedUrl: String) {
        if (feedUrl != feedCachedUrl) {
            downloadData = DownloadData(this, xmlListView)
            downloadData?.execute(feedUrl)
            feedCachedUrl = feedUrl
        } else {
            Log.d("downloadUrl", "downloadUrl - URL not changed")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_URL, feedUrl)
        outState.putInt(STATE_LIMIT, feedLimit)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feed_menu, menu)
        if (feedLimit == 10) {
            menu?.findItem(R.id.menu_top10)?.isChecked = true
        } else {
            menu?.findItem(R.id.menu_top25)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_free -> feedUrl =
                "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplicatoins/limit=%d/xml"

            R.id.menu_paid -> feedUrl =
                "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplicatoins/limit=%d/xml"

            R.id.menu_songs -> feedUrl =
                "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"

            R.id.menu_top10, R.id.menu_top25 -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                } else {

                }
            }

            R.id.menu_refresh -> feedCachedUrl = "INVALIDATED"
            else -> return super.onOptionsItemSelected(item)
        }
        downloadUrl(feedUrl.format(feedLimit))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData?.cancel(true)
    }
}