package com.riki.invinitee

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView

class PDFViewerActivity : AppCompatActivity() {
    private var pdfView : PDFView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfviewer)

        pdfView = findViewById(R.id.pdfView)
        val str : Uri = Uri.parse("http://repository.iainbengkulu.ac.id/4282/1/skripsi%20indah%20pdf.pdf")

        pdfView?.fromUri(str)
            ?.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
            ?.enableSwipe(true) // allows to block changing pages using swipe
            ?.swipeHorizontal(false)
            ?.enableDoubletap(true)
            ?.defaultPage(0)
            ?.enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            ?.password(null)
            ?.scrollHandle(null)
            ?.load();
    }
}