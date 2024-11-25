package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class AddOrEditStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        val student = intent.getParcelableExtra<StudentModel>("student")
        val position = intent.getIntExtra("position", -1)

        student?.let {
            findViewById<EditText>(R.id.edit_text_name).setText(it.studentName)
            findViewById<EditText>(R.id.edit_text_id).setText(it.studentId)
        }

        findViewById<Button>(R.id.button_save).setOnClickListener {
            val name = findViewById<EditText>(R.id.edit_text_name).text.toString()
            val id = findViewById<EditText>(R.id.edit_text_id).text.toString()

            if (name.isNotBlank() && id.isNotBlank()) {
                val resultIntent = Intent()
                resultIntent.putExtra("student", StudentModel(name, id))
                resultIntent.putExtra("position", position)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
