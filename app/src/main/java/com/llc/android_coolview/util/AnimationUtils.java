package com.llc.android_coolview.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class AnimationUtils {

    public static final int BOTTOM_MENU_ANIMATION_DURATION = 200;

    public static final int NEW_EDIT_BOTTOM_MENU_ANIMATION_DURATION = 100;

    private static TranslateAnimation mTranslateRightInAnimation;
    private static TranslateAnimation mTranslateRightOutAnimation;
    private static TranslateAnimation mTranslateInAnimation;
    private static TranslateAnimation mTranslateOutAnimation;
    private static TranslateAnimation mTranslateLeftInAnimation;
    private static TranslateAnimation mTranslateLeftOutAnimation;
    private static TranslateAnimation mTranslateTopOutAnimation;
    private static TranslateAnimation mTranslateTopInAnimation;
    private static AlphaAnimation mAlphaFadeInAnimation;
    private static AlphaAnimation mAlphaFadeOutAnimation;
    private static TranslateAnimation mTranslateBottomInAnimation;
    private static TranslateAnimation mTranslateBottomOutAnimation;

    private static RotateAnimation mRotateAnimationLeftVisible;
    private static RotateAnimation mRotateAnimationLeftHide;
    private static RotateAnimation mRotateAnimationRightVisible;
    private static RotateAnimation mRotateAnimationRightHide;
    @SuppressWarnings("unused")
    private static ScaleAnimation mScaleAnimationOut;
    @SuppressWarnings("unused")
    private static ScaleAnimation mScaleAnimationIn;

    /**view 自旋的animation*/
    private static RotateAnimation mRotateSelfRight;
    private static RotateAnimation mRotateSelfLeft;

    private static AccelerateDecelerateInterpolator mInterpolator;

    static {
        mTranslateTopOutAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF,
                0f);

        mTranslateTopInAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);

        mTranslateRightInAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f);
        mTranslateRightOutAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f);
        mTranslateRightInAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f);
        mTranslateRightOutAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f);

        mTranslateLeftInAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f);

        mTranslateLeftOutAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f);

        mTranslateBottomInAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.48f, Animation.RELATIVE_TO_SELF, 0f);

        mTranslateBottomOutAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.49f);

        mTranslateInAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        mTranslateOutAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);

        mRotateAnimationLeftHide = new RotateAnimation(-90, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        mRotateAnimationLeftVisible = new RotateAnimation(0, -90, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        mRotateAnimationLeftVisible.setDuration(BOTTOM_MENU_ANIMATION_DURATION);
        mRotateAnimationLeftHide.setDuration(BOTTOM_MENU_ANIMATION_DURATION);

        mRotateAnimationRightHide = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 1);
        mRotateAnimationRightVisible = new RotateAnimation(90, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 1);
        mRotateAnimationRightHide.setDuration(BOTTOM_MENU_ANIMATION_DURATION);
        mRotateAnimationRightVisible.setDuration(BOTTOM_MENU_ANIMATION_DURATION);
        mScaleAnimationOut = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                1f);
        mScaleAnimationIn = new ScaleAnimation(1.0f, 0f, 1.0f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                1f);
        mAlphaFadeInAnimation = new AlphaAnimation(1f, 0.5f);
        mAlphaFadeOutAnimation = new AlphaAnimation(0f, 1f);
        mInterpolator = new AccelerateDecelerateInterpolator();
        mAlphaFadeInAnimation.setDuration(BOTTOM_MENU_ANIMATION_DURATION);
        mAlphaFadeOutAnimation.setDuration(BOTTOM_MENU_ANIMATION_DURATION);

        mTranslateBottomInAnimation.setDuration(NEW_EDIT_BOTTOM_MENU_ANIMATION_DURATION);
        mTranslateBottomOutAnimation.setDuration(NEW_EDIT_BOTTOM_MENU_ANIMATION_DURATION);

        /**自旋的动画 */
        mRotateSelfRight = new RotateAnimation(0.0f, +360.0f,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        mRotateSelfRight.setDuration(500);
        mRotateSelfLeft = new RotateAnimation(0.0f, -360.0f,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        mRotateSelfLeft.setDuration(500);
    }
    public static Animation getRightSpinSelfAnimation(){
        return mRotateSelfRight;
    }
    public static Animation getLeftSpinSelfAnimation(){
        return mRotateSelfLeft;
    }

    public static Animation animationComeOutOrBackHorizontal(boolean isAppear, boolean isLeft) {
        AnimationSet set = new AnimationSet(true);
        if (!isAppear) {
            if (isLeft) {
                set.addAnimation(mTranslateLeftInAnimation);
            } else {
                set.addAnimation(mTranslateRightInAnimation);
            }
            set.addAnimation(mAlphaFadeInAnimation);
        } else {
            if (isLeft) {
                set.addAnimation(mTranslateLeftOutAnimation);
            } else {
                set.addAnimation(mTranslateRightOutAnimation);
            }
            set.addAnimation(mAlphaFadeOutAnimation);
        }
        set.setDuration(BOTTOM_MENU_ANIMATION_DURATION);
        set.setInterpolator(mInterpolator);
        return set;
    }

    public static Animation animationComeOutOrIn(boolean isAppear) {
        AnimationSet set = new AnimationSet(true);
        if (!isAppear) {
            set.addAnimation(mTranslateInAnimation);
            set.addAnimation(mAlphaFadeInAnimation);
            set.setDuration(100);
        } else {
            set.addAnimation(mTranslateOutAnimation);
            set.addAnimation(mAlphaFadeOutAnimation);
            set.setDuration(BOTTOM_MENU_ANIMATION_DURATION);
        }
        set.setInterpolator(mInterpolator);
        return set;
    }

    public static Animation animationComeOutOrInFromTop(boolean out) {
        AnimationSet set = new AnimationSet(true);
        if (!out) {
            set.addAnimation(mTranslateTopInAnimation);
            set.setDuration(100);
        } else {
            set.addAnimation(mTranslateTopOutAnimation);
            set.setDuration(200);
        }
        set.setInterpolator(mInterpolator);
        return set;
    }

    public static Animation animationAlphaFade(boolean isAppear) {
        if (!isAppear) {
            return mAlphaFadeInAnimation;
        } else {
            return mAlphaFadeOutAnimation;
        }
    }

    public static Animation animationComeOutOrInFromBottom(boolean out) {
        if (!out) {
            return mTranslateBottomInAnimation;
        } else {
            return mTranslateBottomOutAnimation;
        }
    }

    public static Animation animationRotationLeft(boolean isAppear) {
        if (isAppear) {
            return mRotateAnimationLeftHide;
        } else {
            return mRotateAnimationLeftVisible;
        }
    }

    public static Animation animationRotationRight(boolean isAppear) {
        if (isAppear) {
            return mRotateAnimationRightVisible;
        } else {
            return mRotateAnimationRightHide;
        }
    }

    public static Animation createTranslateAnimation(float xFrom, float xTo, float yFrom,
                                                     float yTo, long duration) {
        Animation trans = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                xFrom, Animation.RELATIVE_TO_SELF, xTo,
                Animation.RELATIVE_TO_SELF, yFrom, Animation.RELATIVE_TO_SELF,
                yTo);
        trans.setDuration(duration);
        return trans;
    }

    public static Animation createTranslateAnimationForT9Pad(float xFrom, float xTo, float yFrom,float yTo){
        AnimationSet set=new AnimationSet(true);
        Animation animation=new TranslateAnimation(xFrom, xTo, yFrom, yTo);
        animation.setDuration(100);
        animation.setFillAfter(true);
        set.addAnimation(animation);
        return set;
    }

    public static AnimatorSet createTranslateAndAlphaForT9Pad(ImageView view,float fromX,float toX){
        AnimatorSet set=new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "x", fromX, toX);
        set.play(anim3).before(anim1);
        set.play(anim1).with(anim2);
        set.setDuration(150);
        return set;
    }

    public static AnimatorSet createAlphaAndTranslateForT9Pad(ImageView view,float fromX,float toX){
        AnimatorSet set=new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "x", fromX, toX);
        set.play(anim3).after(anim1);
        set.play(anim1).with(anim2);
        set.setDuration(150);
        return set;
    }

    public static AnimatorSet createTranslateForTickets(View view,float fromX,float toX){
        AnimatorSet set=new AnimatorSet();
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "x", fromX, toX);
        set.play(anim3);
        set.setDuration(200);
        return set;
    }

    public static Animation createAlphaAnimationForT9Pad(boolean flag,long duration){
        AnimationSet set=new AnimationSet(true);
        Animation animation=null;
        if(flag){
            animation=new AlphaAnimation(0.0f, 1.0f);
        }else{
            animation=new AlphaAnimation(1.0f, 0.0f);
        }
        animation.setDuration(duration);
        animation.setFillAfter(true);
        set.addAnimation(animation);
        return set;
    }

    public static Animation createScaleAnimationForT9pad(boolean flag,long duration){
        AnimationSet set=new AnimationSet(true);
        Animation animation=null;
        if(flag){
            // 从小变大
            animation=new ScaleAnimation(
                    0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }else{
            // 从大变小
            animation=new ScaleAnimation(
                    1.0f, 0.0f, 1.0f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
            );
        }
        animation.setDuration(duration);
        animation.setFillAfter(true);
        set.addAnimation(animation);
        return set;
    }

    public static int getFromXOrToX(Context mContext,View view,boolean flag){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 获取屏幕分辨率宽度
        int mWidth=view.getWidth();
        // 获取平移的坐标
        int toX=((width-mWidth)/2);
        float scale = mContext.getResources().getDisplayMetrics().density;
        int fromX=width-mWidth-((int) (10*scale+0.5f));
        if(flag){
            return toX;
        }else{
            return fromX;
        }
    }

    public static Animation createRotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ,
                                                    boolean reverse) {
        Animation trans = new Rotate3dAnimation(fromDegrees, toDegrees, centerX, centerY, depthZ, true);
        return trans;
    }

    public static Animation createTranslateAnimation(float xFrom, float xTo, float yFrom,float yTo){
        Animation trans = new TranslateAnimation(Animation.RELATIVE_TO_SELF,xFrom, Animation.RELATIVE_TO_SELF, xTo,Animation.RELATIVE_TO_SELF, yFrom, Animation.RELATIVE_TO_SELF,yTo);
        trans.setDuration(200);
        return trans;
    }

}

class Rotate3dAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;

    /**
     * Creates a new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around a center point on the 2D space, definied by a pair of
     * X and Y coordinates, called centerX and centerY. When the animation
     * starts, a translation on the Z axis (depth) is performed. The length of
     * the translation can be specified, as well as whether the translation
     * should be reversed in time.
     *
     * @param fromDegrees
     *                the start angle of the 3D rotation
     * @param toDegrees
     *                the end angle of the 3D rotation
     * @param centerX
     *                the X center of the 3D rotation
     * @param centerY
     *                the Y center of the 3D rotation
     * @param reverse
     *                true if the translation should be reversed, false
     *                otherwise
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ,
                             boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
