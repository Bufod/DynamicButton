package com.example.dynamicbutton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class TransitionBtEffects {
    private Context context;
    private Button mainBt; //главная кнопка которая распадается на список

    public TransitionBtEffects(Context context, Button mainBt) {
        this.context = context;
        this.mainBt = mainBt;
        addClickActionMainBt();
    }

    // назначаем слушателя для начальной кнопки
    private void addClickActionMainBt(){
        mainBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //запускаем замену представления кнопки контейнером FrameLayout
                replaceBtWithFrameLayout(view);
            }
        });
    }

    // замена представления кнопки контейнером FrameLayout
    private void replaceBtWithFrameLayout(View view){
        ViewGroup parent = (ViewGroup) view.getParent(); //получение корневой разметки
        int idBt = parent.indexOfChild(view); //получение id кнопки на разметке
        ViewGroup.LayoutParams paramsBt = view.getLayoutParams(); //получение привязок к элементам
                                                                  //от кнопки
        parent.removeViewAt(idBt); //удаляем кнопку
        FrameLayout container = new FrameLayout(context); //создаем контейнер
        //надуваем контейнер разметкой со списком кнопок
        container.addView((
                (Activity)context)
                .getLayoutInflater()
                .inflate(R.layout.list_button, parent, false)
        );
        //назначаем им слушателя
        setOnClickListenerToListBt(container);
        container.setLayoutParams(paramsBt); //задаем списку привязки унаследованные
                                             // от представления кнопки
        parent.addView(container, idBt); //добавляем контейнер на корневую разметку
    }

    //циклическое задание слушателей для списка кнопок
    private void setOnClickListenerToListBt(ViewGroup container){
        ViewGroup basicLayout = (ViewGroup) container.getChildAt(0);
        for (int i = 0; i < basicLayout.getChildCount(); i++) {
            ((Button) basicLayout.getChildAt(i))
                    .setOnClickListener(onClickListener());
        }
    }

    //слушаеть кликов с выбором действия
    private View.OnClickListener onClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (view.getId()){
                    case R.id.mainActBt:
                        intent = new Intent(context, MainActivity.class);
                        break;
                    case R.id.secondActBt:
                        intent = new Intent(context, SecondActivity.class);
                        break;
                    case R.id.thirdActBt:
                        intent = new Intent(context, ThirdActivity.class);
                        break;
                }
                if (intent != null)
                    context.startActivity(intent);
            }
        };
    }


}
