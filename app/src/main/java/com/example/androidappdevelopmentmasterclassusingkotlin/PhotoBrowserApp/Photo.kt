package com.example.androidappdevelopmentmasterclassusingkotlin.PhotoBrowserApp

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.ObjectStreamException
import java.io.Serializable

class Photo(
    var title: String,
    var author: String,
    var authorId: String,
    var link: String,
    var tags: String,
    var image: String
) : Parcelable {
    constructor(parcel: Parcel):this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    companion object CREATOR: Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel?): Photo? {
            return Photo(parcel)
        }
    }

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorId='$authorId', link='$link', tags='$tags', image='$image')"
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

//    @Throws(IOException::class)
//    private fun writeObject(out: ObjectOutputStream) {
//        out.writeUTF(title)
//        out.writeUTF(author)
//        out.writeUTF(authorId)
//        out.writeUTF(link)
//        out.writeUTF(tags)
//        out.writeUTF(image)
//    }
//
//    @Throws(IOException::class, ClassNotFoundException::class)
//    private fun readObject(inStream: ObjectInputStream) {
//        title = inStream.readUTF()
//        author = inStream.readUTF()
//        authorId = inStream.readUTF()
//        link = inStream.readUTF()
//        tags = inStream.readUTF()
//        image = inStream.readUTF()
//    }
//
//    @Throws(ObjectStreamException::class)
//    private fun readObjectNoData() {
//        Log.d("Photo", "readObjectNoData called")
//    }
}