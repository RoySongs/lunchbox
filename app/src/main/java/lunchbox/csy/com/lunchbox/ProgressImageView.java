package lunchbox.csy.com.lunchbox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ProgressImageView extends ImageView {

    int count = 0;
    boolean isStop = false;

    int progressList[] = {
            R.drawable.ic_ptr_orange_1,
            R.drawable.ic_ptr_gray_1,
            R.drawable.ic_ptr_orange_2,
            R.drawable.ic_ptr_gray_2,
            R.drawable.ic_ptr_orange_3,
            R.drawable.ic_ptr_gray_3,
            R.drawable.ic_ptr_orange_4,
            R.drawable.ic_ptr_gray_4
    };

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setImageResource(progressList[count]);
        startAnimation();
    }

    private void startAnimation() {

        if (isStop) {
            return;
        }

        count++;
        count %= progressList.length;
        setImageResource(progressList[count]);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            isStop = false;
            startAnimation();
        } else if (visibility == GONE) {
            isStop = true;
        }
    }
}
