package com.sena.proyecto.utils

object Constants {
    // Emulador Android usa 10.0.2.2 para acceder a localhost del host
    const val BASE_URL = "http://10.0.2.2:8080/api/"

    // SharedPreferences
    const val PREFS_NAME = "maquimu_prefs"
    const val PREF_TOKEN = "jwt_token"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_NAME = "user_name"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_USER_ROL = "user_rol"
    const val PREF_CLIENTE_ID = "cliente_id"
}
