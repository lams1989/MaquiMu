package com.sena.proyecto.utils

object Constants {
    // Para desarrollo local con emulador, usar:
    // const val BASE_URL = "http://10.0.2.2:8080/api/"
    const val BASE_URL = "https://maquimu.onrender.com/api/"

    // SharedPreferences
    const val PREFS_NAME = "maquimu_prefs"
    const val PREF_TOKEN = "jwt_token"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_NAME = "user_name"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_USER_ROL = "user_rol"
    const val PREF_CLIENTE_ID = "cliente_id"
}
