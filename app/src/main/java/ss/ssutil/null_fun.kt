package ss.ssutil


data class User(
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = ""
)

fun foo1(user1: User) {
    println(user1.firstName)
}

fun foo2(user1: User?) {
    if (user1 == null) {

        println(user1)
        println("no name")
    } else {
        println(user1.firstName)
    }

}

fun foo3(user1: User?) {


//    if(user1 == null) throw NullPointerException()
//    val u = user1!!


    val user = user1 ?: User()

    val userName = user.userName
    val userName1 = user1?.userName

//    val xx  = a?.b?.c?.d  ?: "fallback"




}
