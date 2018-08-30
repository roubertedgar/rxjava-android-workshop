package workshop.com.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

fun TextView.onTextChange(textWatcher: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {}

        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            textWatcher(text.toString())
        }

    })
}