package org.techtown.lottosimulator_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    컴퓨터가 뽑은 당첨번호 6개를 저장할 ArrayList
    val mWinNumberList = ArrayList<Int>()

//    당첨번호를 보여줄 6개의 텍스트뷰를 담아 둘 ArrayList
    val mWinNumTextViewList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setValues()


    }

    private fun setupEvents(){

        btnBuyLotto.setOnClickListener {

            buyLotto()

        }

    }

    private fun buyLotto(){

//        6개의 당첨 번호 생성
//        코틀린의 for 문은 for-each 문법 기반

//        ArrayList 는 목록을 계속 누적 가능.
//        당첨번호 뽑기 전에 기존 당첨번호 삭제 후 다시 뽑기

        mWinNumberList.clear()

        for (i in 0 until 6 ){           // for (i in 0..5)

//            괜찮은 번호가 나올 때 까지 무한 반복
            while(true){

//                1~45 랜덤숫자
//                Math.random()은 0~1 => 1~45로 가공(Int 캐스팅)
                val randomNum = (Math.random() * 45 + 1).toInt()
//                중복 검사 통과시 while 깸
                if(!mWinNumberList.contains(randomNum)){            // !mWinNumberList.들어있다?(randomNum)
//                  당첨 번호로, 뽑은 램덤 숫자 등록
                    mWinNumberList.add( randomNum )
                    break
                }
           }
        }

//        만들어 진 당첨번호 6개를 -> 작은수~큰수로 정리해서 -> 텍스트뷰에 표현

        mWinNumberList.sort()                     // 자바로 직접 짜던 로직 > 객체지향 특성, 만들어져 있는 기능 활용

        Log.d("당첨번호", mWinNumberList.toString())

//        for > 돌면서, 당첨번호도 / 몇번째 바퀴인지도 필요 => 텍스트뷰를 찾아내야 함.

        mWinNumberList.forEachIndexed { index, winNum ->
//            순서에 맞는 텍스트뷰 추출 => 문구로 당첨번호 설정}

            mWinNumTextViewList[index].text = winNum.toString()

        }

//        보너스번호 생성

//        텍스트뷰에 배치
    }

    private fun setValues(){

        mWinNumTextViewList.add(txtWinNum01)
        mWinNumTextViewList.add(txtWinNum02)
        mWinNumTextViewList.add(txtWinNum03)
        mWinNumTextViewList.add(txtWinNum04)
        mWinNumTextViewList.add(txtWinNum05)
        mWinNumTextViewList.add(txtWinNum06)

    }
}