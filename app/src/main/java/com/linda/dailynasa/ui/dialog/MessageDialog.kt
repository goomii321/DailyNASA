package com.linda.dailynasa.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.linda.dailynasa.R

class MessageDialog(context: Context):Dialog(context) {

    private lateinit var dialogView: View
    private lateinit var textView: TextView

    companion object{
        var dialog: Dialog? = null
    }
    fun dismissPopup() = dialog?.let { it.dismiss() }

    init {
        dialog = Dialog(context)
        setCanceledOnTouchOutside(true)
    }

    interface onClickListener:View.OnClickListener

    var message:String = ""
    var leftButtonState:String? = null
    var rightButtonState:String? = null

    fun setCustomDialog(
        leftListener: View.OnClickListener?,
        rightListener: View.OnClickListener?) {

        dialog?.let { dialog ->
            dialogView = layoutInflater.inflate(R.layout.dialog_message, null)
            val rightButton = dialogView.findViewById<Button>(R.id.right_button)
            val leftButton = dialogView.findViewById<Button>(R.id.left_button)
            textView = dialogView.findViewById<TextView>(R.id.message_text)
            dialog.setContentView(dialogView)

            textView.text = message

            if (leftButtonState == null) {
                leftButton.visibility = View.GONE
            } else {
                leftButton.visibility = View.VISIBLE
                leftButton.text = leftButtonState
            }

            if (rightButtonState == null) {
                rightButton.visibility = View.GONE
            } else {
                rightButton.visibility = View.VISIBLE
                rightButton.text = rightButtonState
            }

            leftButton.setOnClickListener(leftListener)
            rightButton.setOnClickListener(rightListener)

            dialog.show()
        }
    }
}