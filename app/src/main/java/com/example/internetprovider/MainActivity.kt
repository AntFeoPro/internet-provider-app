package com.example.internetprovider

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internetprovider.databinding.ActivityMainBinding
import com.example.internetprovider.model.Tariff

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val tariffAdapter = TariffAdapter()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupTariffs()
    }
    
    private fun setupUI() {
        // Настройка RecyclerView для тарифов
        binding.tariffsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = tariffAdapter
        }
        
        // Кнопка оплаты
        binding.paymentButton.setOnClickListener {
            val paymentUrl = "https://payberry.ru/pay?id=90&group=114&utm_source=gipernet.pro&utm_medium=referral&utm_campaign=gipernet.pro&utm_referrer=gipernet.pro"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl))
            startActivity(intent)
        }
        
        // Кнопка отправки email
        binding.contactButton.setOnClickListener {
            sendEmail()
        }
    }
    
    private fun setupTariffs() {
        val tariffs = listOf(
            Tariff("Частный сектор", "Базовый", "100 Мбит/с", "500 ₽/мес"),
            Tariff("Частный сектор", "Премиум", "300 Мбит/с", "800 ₽/мес"),
            Tariff("Многоквартирный дом", "Стандарт", "200 Мбит/с", "600 ₽/мес"),
            Tariff("Многоквартирный дом", "Максимум", "500 Мбит/с", "900 ₽/мес"),
            Tariff("Юридические лица", "Бизнес", "1000 Мбит/с", "2000 ₽/мес"),
            Tariff("Юридические лица", "Корпоративный", "2000 Мбит/с", "5000 ₽/мес")
        )
        tariffAdapter.submitList(tariffs)
    }
    
    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@provider.ru"))
            putExtra(Intent.EXTRA_SUBJECT, "Вопрос интернет-провайдеру")
            putExtra(Intent.EXTRA_TEXT, "Здравствуйте! У меня вопрос:\n\n")
        }
        
        try {
            startActivity(Intent.createChooser(emailIntent, "Отправить email"))
        } catch (e: Exception) {
            // Обработка ошибки
        }
    }
}
