package org.techtown.lottosimulator_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    내 번호 6개
//    코틀린은 단순 배열 초기화 int[] arr = {  } 문법 지원 x

//    숫자 목록을 파라미터로 넣으면 > Array 로 만들어주는 함수 실행
   val mMyNumbers = arrayOf(13, 17, 23, 27, 36, 41)

//    컴퓨터가 뽑은 당첨번호 6개를 저장할 ArrayList
    val mWinNumberList = ArrayList<Int>()
    var mBonusNum = 0                        // 보너스번호는 매 판마다 새로 뽑아야 함. 변경 O

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


//        for > 돌면서, 당첨번호도 / 몇번째 순서인지도 필요 => 텍스트뷰를 찾아내야 함.
        mWinNumberList.forEachIndexed { index, winNum ->

//            순서에 맞는 텍스트뷰 추출 => 문구로 당첨번호 설정}
            mWinNumTextViewList[index].text = winNum.toString()

        }

//        보너스번호 생성 -> 1~45 하나, 당첨번호와 겹치지 않게.

        while(true){
            val randomNum = (Math.random()*45+1).toInt()

            if (!mWinNumberList.contains(randomNum)){       // 겹치지 않는 숫자 뽑기
                mBonusNum = randomNum
                break
            }
        }

//        텍스트뷰에 배치
        txtBonusNum.text = mBonusNum.toString()

//        내 숫자 6개와 비교, 등수 판정

        checkLottoRank()

    }

    private  fun checkLottoRank(){

        //  내 번호 목록 / 당첨 번호 목록 중 같은 숫자가 몇개?
         var correctCount = 0

    }

    private fun setValues(){

        mWinNumTextViewList.add(txtWinNum01)            // textViewList 목록에 textView 담아줌
        mWinNumTextViewList.add(txtWinNum02)
        mWinNumTextViewList.add(txtWinNum03)
        mWinNumTextViewList.add(txtWinNum04)
        mWinNumTextViewList.add(txtWinNum05)
        mWinNumTextViewList.add(txtWinNum06)

    }
}