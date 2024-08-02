package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.EditProfile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.EditProfileUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.GetProfileUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.UploadImageUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getProfileUc: GetProfileUC,
    private val uploadImageUC: UploadImageUC,
    private val editProfileUC: EditProfileUC,
) : ViewModel() {
    private val _state = MutableStateFlow(EditProfileState())
    private var navController: NavController? = null

    val state: StateFlow<EditProfileState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isGetProfileLoading = true)

            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                return@launch
            }
            val result =
                getProfileUc.getProfile(token)
            result.fold(
                onSuccess = { profileResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        profileResponse = profileResponse
                    )
//                    Log.d("ProfileViewModel", "getProfile: $profileResponse")
                    _state.value = _state.value.copy(isGetProfileLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ProfileViewModel", "getProfile: $exception")
                    _state.value = _state.value.copy(isGetProfileLoading = false)
                }
            )
        }
    }

    fun uploadImage(file: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUploadImageLoading = true)

            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                return@launch
            }
            val result =
                uploadImageUC.uploadImage(token, file)
            result.fold(
                onSuccess = { imageResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        imageResponse = imageResponse
                    )
//                    Log.d("ProfileViewModel", "uploadImage: $imageResponse")
                    _state.value = _state.value.copy(isUploadImageLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ProfileViewModel", "uploadImage: $exception")
                    _state.value = _state.value.copy(isUploadImageLoading = false)
                }
            )
        }
    }

    fun createMultipartBody(uri: Uri, multipartName: String): MultipartBody.Part? {
        val bitmap = getBitmapFromUri(uri) ?: return null
        val file = File(uri.path!!)
        val os: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.close()

        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = navController?.context?.contentResolver?.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("EditProfileViewModel", "Error getting bitmap from URI: $e")
            null
        }
    }

    fun editProfile(
        avatar: String?,
        name: String?,
        bio: String?,
        birthday: String?,
        file: MultipartBody.Part?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUpdateProfileSuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                return@launch
            }

            withContext(Dispatchers.IO) {
                if (file != null) {
                    uploadImage(file)
                }

                delay(1000)// for upload image
                _state.value = _state.value.copy(isLoading = true)



                val pathRequest = mutableListOf<PathRequest>()
                if (_state.value.imageResponse != null) {
                    pathRequest.add(PathRequest(_state.value.imageResponse?.images?.get(0).toString(), "avatar", "replace"))
                }
                if (name != null && name != _state.value.profileResponse?.name) {
                    pathRequest.add(PathRequest(name, "name", "replace"))
                }
                if (bio != null && bio != _state.value.profileResponse?.bio) {
                    pathRequest.add(PathRequest(bio, "bio", "replace"))
                }


                val response =
                    editProfileUC.updateProfile(token, pathRequest)
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(isUpdateProfileSuccess = true)
                    _state.value = _state.value.copy(isLoading = false)
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                    Log.d(
                        "EditProfileViewModel",
                        "editProfile: ${response.errorBody()?.string()}"
                    )
//                _state.value = _state.value.copy(error = response.errorBody()?.string())
                }
            }

        }
    }

}