package akw.app6

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

enum class Perm(val id: String, val requestCode: Int) {
    GetContacts(Manifest.permission.READ_CONTACTS, 2345)
}

object Perms {

    const val TAG = "Perms"
    fun checkPerms(activity: Activity,perm: Perm) {
        val perms: Array<String> = arrayOf(perm.id)
        ActivityCompat.requestPermissions(activity, perms, perm.requestCode)
//        map[perm.requestCode] = result
    }

    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, grantResults: IntArray) {
        Log.v(TAG, "onRequestPermissionsResult: $requestCode");

            val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (!activity.isDestroyed) {

            }


    }

}