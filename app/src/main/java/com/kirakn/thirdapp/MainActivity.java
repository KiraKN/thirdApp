package com.kirakn.thirdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // задание полей
    float apartmentPrice = 14_000; // стоимость телескопа
    int account = 1_000; // счёт пользователя
    float wage = 2_500; // стипендия в месяц
    int percentFree = 100; // доля заработной платы на любые траты
    float percentBank = 5; // годовая процентная ставка
    float[] monthlyPayments = new float[120]; // создание массива ежемесячных платежей на 10 лет

    // метод подсчёта стоимости телескопа с учётом первоначального взноса
    private float apartmentPriceWithContribution() {
        return apartmentPrice - account; // возврат подсчитанного значения
    }

    // метод подсчёта ежемесячных трат на телескоп (зар.плата, процент своб.трат)
    public float mortgageCosts(float amount, int percent) {
        return (amount*percent)/100;
    }

    // метод подсчёта времени выплаты вклада (сумма долга, сумма платежа, годовой процент)
    // и заполнение массива monthlyPayments[] ежемесячными платежами
    public int countMonth(float total, float mortgageCosts, float percentBankYear) {

        float percentBankMonth = percentBankYear / 12; // подсчёт ежемесячного процента банка за вклад
        int count = 0; // счётчик месяцев выплаты вклада

        // алгоритм расчёта вклада
        while (total>0) {
            count++; // добавление нового месяца платежа
            total = (total + (total*percentBankMonth)/100) - mortgageCosts; // вычисление долга с учётом выплаты и процента
            // заполнение массива ежемесячными платежами за ипотеку
            if(total > mortgageCosts) { // если сумма долга больше ежемесячного платежа, то
                monthlyPayments[count-1] = mortgageCosts; // в массив добавляется целый платёж
            } else { // иначе
                monthlyPayments[count-1] = total; // в массив добавляется платёж равный остатку долга
            }
        }

        return count;
    }

    // создание дополнительных полей для вывода на экран полученных значений
    private TextView countOut; // поле вывода количества месяцев выплаты ипотеки
    private TextView manyMonthOut; // поле выписки по ежемесячным платежам

    // вывод на экран полученных значений
    @Override
    protected void onCreate(Bundle savedInstanceState) { // создание жизненного цикла активности
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // присваивание жизненному циклу активити представления activity_main

        // присваивание переменным активити элементов представления activity_main
        countOut = findViewById(R.id.countOut); // вывод информации количества месяцев выплаты вклада
        manyMonthOut = findViewById(R.id.manyMonthOut); // вывод информации выписки по ежемесячным платежам

        // запонение экрана
        // 1) вывод количества месяцев выплаты вкладу
        countOut.setText(countMonth(apartmentPriceWithContribution(), mortgageCosts(wage, percentFree),percentBank) + " месяцев");
        // 2) подготовка выписки
        String monthlyPaymentsList = "";
        for(float list : monthlyPayments) {
            if (list > 0) {
                monthlyPaymentsList = monthlyPaymentsList + Float.toString(list) + " монет ";
            } else {
                break;
            }
        }
        // 3) вывод выписки ежемесячных выплат по влкаду
        manyMonthOut.setText("Первоначальный взнос " + account + " монет, ежемесячные выплаты: " + monthlyPaymentsList);
    }
}