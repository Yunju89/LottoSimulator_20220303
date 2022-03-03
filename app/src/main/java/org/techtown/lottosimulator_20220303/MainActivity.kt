package org.techtown.lottosimulator_20220303

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
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

//    사용한 금액, 당첨된 금액 합산 변수
    var mUsedMoney = 0
    var mEarnMoney = 0L     // 30억 이상 담첨 대비. Long 타입설정

//    각 등수별 횟수 카운팅 변수
    var rankCount1 = 0
    var rankCount2 = 0
    var rankCount3 = 0
    var rankCount4 = 0
    var rankCount5 = 0
    var rankCountFail = 0

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

//        사용 금액 늘려주기
        mUsedMoney += 1000

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

    private  fun checkLottoRank() {

//        내 번호 목록 / 당첨 번호 목록 중 같은 숫자가 몇개?
        var correctCount = 0

//        내 번호를 하나씩 조회
        for (myNum in mMyNumbers) {
//            당첨번호를 맞췄는가? => 당첨 목록에 내 번호가 들어있나?
            if (mWinNumberList.contains(myNum)) {
                correctCount++
            }
        }
//        맞춘 갯수에 따른 등수 판정

        when (correctCount) {
            6 -> {
//                30억을 번 금액으로 추가
                mEarnMoney += 3000000000
                rankCount1++
            }
            5 -> {
//                보너스 번호를 맞췄는지? => 보너스 번호가 내 번호 목록에 들어있나?
                if (mMyNumbers.contains(mBonusNum)) {
                    mEarnMoney += 50000000
                    rankCount2++

                } else {
                    mEarnMoney += 2000000
                    rankCount3++
                }
            }
            4 -> {
                mEarnMoney += 50000
                rankCount4++
            }
            3 -> {
//                5등 -> 5천원 사용한 돈을 줄여주자.
                mUsedMoney -= 5000
                rankCount5++
            }
            else -> {
                rankCountFail++
            }

        }
//        사용금액 / 당첨금액 텍스트뷰에 각각 반영

        txtUsedMoney.text = "${NumberFormat.getInstance().format(mUsedMoney)} 원"
        txtEarnMoney.text = "${NumberFormat.getInstance().format(mEarnMoney)} 원"

//        등수별 당첨 횟수 텍스트뷰 반영
        txtRankCount1.text ="${rankCount1} 회"
        txtRankCount2.text ="${rankCount2} 회"
        txtRankCount3.text ="${rankCount3} 회"
        txtRankCount4.text ="${rankCount4} 회"
        txtRankCount5.text ="${rankCount5} 회"
        txtRankCountFail.text ="${rankCountFail} 회"

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
