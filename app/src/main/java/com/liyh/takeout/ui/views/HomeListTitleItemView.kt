package com.liyh.takeout.ui.views

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.daimajia.slider.library.Tricks.ViewPagerEx
import com.liyh.takeout.R
import kotlinx.android.synthetic.main.item_title.view.*


/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 24 日
 * @time  10 时 24 分
 * @descrip :
 */
class HomeListTitleItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs), BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onSliderClick(slider: BaseSliderView?) {

    }

    init {
        View.inflate(context, R.layout.item_title, this)
    }

    fun setData(url_maps: HashMap<String, String>) {


//        val file_maps = HashMap<String, Int>()
//        file_maps["Hannibal"] = R.drawable.hannibal
//        file_maps["Big Bang Theory"] = R.drawable.bigbang
//        file_maps["House of Cards"] = R.drawable.house
//        file_maps["Game of Thrones"] = R.drawable.game_of_thrones
        slider.removeAllSliders()
        for ((key, value) in url_maps) {
            val textSliderView = TextSliderView(context)
            // initialize a SliderLayout
            textSliderView
                    .description(key)
                    .image(value)
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this)

            //add your extra information
            textSliderView.bundle(Bundle())
            textSliderView.bundle
                    .putString("extra", key)

            slider.addSlider(textSliderView)
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        slider.setCustomAnimation(DescriptionAnimation())
        slider.setDuration(4000)
        slider.addOnPageChangeListener(this)
    }

}