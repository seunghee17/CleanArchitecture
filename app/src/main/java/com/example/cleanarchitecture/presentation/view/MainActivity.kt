package com.example.cleanarchitecture.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.data.network.ApiResult
import com.example.cleanarchitecture.databinding.ActivityMainBinding
import com.example.cleanarchitecture.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getResponse.asLiveData().observe(this){response->
            //응답에 따른 ui세팅
            Toast.makeText(this,"정상호출!",Toast.LENGTH_SHORT).show()
        }
        //flow의 데이터 변화를 livedata형태로 변환해 관찰
        viewModel.errorState.asLiveData().observe(this){
            it?.let{
                when(it){
                    is ApiResult.Failure.HttpError -> Toast.makeText(this,"${it}번 에러 발생",Toast.LENGTH_SHORT).show()
                    //api오류일때
                    is ApiResult.Failure.NetWorkError -> Toast.makeText(this,"네트워크 상태 확인",Toast.LENGTH_SHORT).show()
                    is ApiResult.Failure.UnknownApiError -> Toast.makeText(this,"이유 미상",Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loading.asLiveData().observe(this){isloading->
            if(isloading){
                Toast.makeText(this,"로딩중",Toast.LENGTH_SHORT).show()
            }
            else{

            }

        }
        binding.text.setOnClickListener{
            viewModel.getResponse()
        }

    }
}