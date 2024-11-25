package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )
  private lateinit var adapter: StudentListAdapter

  companion object {
    const val REQUEST_ADD_STUDENT = 1
    const val REQUEST_EDIT_STUDENT = 2
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Gắn ListView và Adapter
    val listView: ListView = findViewById(R.id.list_view_students)
    adapter = StudentListAdapter(this, students)
    listView.adapter = adapter

    // Đăng ký ContextMenu cho ListView
    registerForContextMenu(listView)

    // Xử lý sự kiện click vào ListView
    listView.setOnItemClickListener { _, _, position, _ ->
      Toast.makeText(
        this,
        "Selected: ${students[position].studentName}",
        Toast.LENGTH_SHORT
      ).show()
    }
  }

  // Tạo Option Menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  // Xử lý sự kiện trong Option Menu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_new -> {
        val intent = Intent(this, AddOrEditStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_ADD_STUDENT)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  // Tạo Context Menu
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  // Xử lý sự kiện trong Context Menu
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position

    when (item.itemId) {
      R.id.menu_edit -> {
        val intent = Intent(this, AddOrEditStudentActivity::class.java)
        intent.putExtra("student", students[position])
        intent.putExtra("position", position)
        startActivityForResult(intent, REQUEST_EDIT_STUDENT)
        return true
      }
      R.id.menu_remove -> {
        students.removeAt(position)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
        return true
      }
    }
    return super.onContextItemSelected(item)
  }

  // Xử lý kết quả trả về từ Activity
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      val student = data.getParcelableExtra<StudentModel>("student") ?: return
      val position = data.getIntExtra("position", -1)

      when (requestCode) {
        REQUEST_ADD_STUDENT -> {
          students.add(student)
          adapter.notifyDataSetChanged()
        }
        REQUEST_EDIT_STUDENT -> {
          if (position >= 0) {
            students[position] = student
            adapter.notifyDataSetChanged()
          }
        }
      }
    }
  }
}
