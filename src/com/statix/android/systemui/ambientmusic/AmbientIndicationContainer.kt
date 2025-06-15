package com.google.android.systemui.ambientmusic

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.PendingIntent
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.media.MediaMetadata
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.MathUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

import com.android.app.animation.Interpolators
import com.android.keyguard.KeyguardUpdateMonitor
import com.android.systemui.AutoReinflateContainer
import com.android.systemui.Dependency
import com.android.systemui.doze.DozeReceiver
import com.android.systemui.media.NotificationMediaManager
import com.android.systemui.plugins.ActivityStarter
import com.android.systemui.plugins.statusbar.StatusBarStateController
import com.android.systemui.power.domain.interactor.PowerInteractor
import com.android.systemui.util.wakelock.WakeLock

import com.statix.android.systemui.dagger.SysUIComponentStatix.SwitchingProvider
import com.statix.android.systemui.res.R

/* compiled from: go/retraceme 79c91a765fa8c1915dda7c2f59c3b3b45c4a561d5cd3d2423a94ab79ebc5509f */
/* loaded from: classes2.dex */
class AmbientIndicationContainer(
    context: Context,
    attributeSet: AttributeSet? = null
) : AutoReinflateContainer(context, attributeSet), DozeReceiver, StatusBarStateController.StateListener, NotificationMediaManager.MediaListener {

    private var activityStarter: ActivityStarter? = null
    private var ambientIconOverride: Drawable? = null
    private var ambientIndicationContainer: ConstraintLayout? = null
    private var ambientIndicationIconSize = 0
    private lateinit var ambientMusicAnimation: Drawable? = null
    private lateinit var ambientMusicNoteIcon: Drawable? = null
    private var ambientMusicNoteIconIconSize = 0
    private var ambientMusicText: CharSequence? = null
    private var ambientSkipUnlock = false
    private var delayedWakeLockFactory: SwitchingProvider.AnonymousClass39? = null
    private var dozing = false
    private var favoritingIntent: PendingIntent? = null
    private val handler: Handler
    private val iconBounds: Rect
    private var iconDescription: String? = null
    private var iconOverride = -1
    private lateinit var iconView: ImageView? = null
    private var indicationTextMode = 0
    private var keyguardUpdateMonitor: KeyguardUpdateMonitor? = null
    private var mediaPlaybackState = 0
    private var openIntent: PendingIntent? = null
    private var powerInteractor: PowerInteractor? = null
    private var reverseChargingAnimation: Drawable? = null
    private var reverseChargingMessage: CharSequence? = null
    private var statusBarState = 0
    private var textColor = 0
    private var textColorAnimator: ValueAnimator? = null
    private lateinit var textView: TextView? = null
    private var wakeLock: WakeLock? = null
    private var wirelessChargingMessage: CharSequence? = null

    companion object {
        @JvmField
        val `$r8$clinit` = 0

        fun `$r8$lambda$DFan0h9JQgIimo3ogLWaY_C9MMU`(ambientIndicationContainer: AmbientIndicationContainer) {
            val i = 1
            ambientIndicationContainer.textView = ambientIndicationContainer.findViewById(R.id.ambient_indication_text)
            ambientIndicationContainer.iconView = ambientIndicationContainer.findViewById(R.id.ambient_indication_icon)
            ambientIndicationContainer.ambientIndicationContainer = ambientIndicationContainer.findViewById(R.id.ambient_indication)
            val constraintSet = ConstraintSet()
            if (ambientIndicationContainer.keyguardUpdateMonitor!!.isUdfpsSupported()) {
                constraintSet.load(R.xml.ambient_indication_inner_downwards, (ambientIndicationContainer as FrameLayout).mContext)
            } else {
                constraintSet.load(R.xml.ambient_indication_inner_upwards, (ambientIndicationContainer as FrameLayout).mContext)
            }
            constraintSet.applyTo(ambientIndicationContainer.ambientIndicationContainer)
            ambientIndicationContainer.ambientMusicAnimation = null
            ambientIndicationContainer.ambientMusicNoteIcon = null
            ambientIndicationContainer.reverseChargingAnimation = null
            ambientIndicationContainer.textView!!.currentTextColor.also { ambientIndicationContainer.textColor = it }
            ambientIndicationContainer.resources.getDimensionPixelSize(R.dimen.ambient_indication_icon_size).also { ambientIndicationContainer.ambientIndicationIconSize = it }
            ambientIndicationContainer.resources.getDimensionPixelSize(R.dimen.ambient_indication_note_icon_size).also { ambientIndicationContainer.ambientMusicNoteIconIconSize = it }
            ambientIndicationContainer.textView!!.isEnabled = !ambientIndicationContainer.dozing
            ambientIndicationContainer.updateColors()
            ambientIndicationContainer.updatePill()
            val i2 = 0
            ambientIndicationContainer.textView!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    val i3 = i2
                    when (i3) {
                        0 -> {
                            val i4 = AmbientIndicationContainer.`$r8$clinit`
                            ambientIndicationContainer.onTextClick()
                        }
                        else -> if (ambientIndicationContainer.favoritingIntent == null) {
                            ambientIndicationContainer.onTextClick()
                        } else {
                            ambientIndicationContainer.powerInteractor!!.wakeUpIfDozing(4, "AMBIENT_MUSIC_CLICK")
                            sendBroadcastWithoutDismissingKeyguard(ambientIndicationContainer.favoritingIntent)
                        }
                    }
                }
            })
            ambientIndicationContainer.iconView!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    val i3 = i
                    when (i3) {
                        0 -> {
                            val i4 = AmbientIndicationContainer.`$r8$clinit`
                            ambientIndicationContainer.onTextClick()
                        }
                        else -> if (ambientIndicationContainer.favoritingIntent == null) {
                            ambientIndicationContainer.onTextClick()
                        } else {
                            ambientIndicationContainer.powerInteractor!!.wakeUpIfDozing(4, "AMBIENT_MUSIC_CLICK")
                            sendBroadcastWithoutDismissingKeyguard(ambientIndicationContainer.favoritingIntent)
                        }
                    }
                }
            })
        }

        fun sendBroadcastWithoutDismissingKeyguard(pendingIntent: PendingIntent?) {
            if (pendingIntent!!.isActivity) {
                return
            }
            try {
                pendingIntent.send()
            } catch (e: PendingIntent.CanceledException) {
                Log.w("AmbientIndication", "Sending intent failed: $e")
            }
        }
    }

    init {
        iconBounds = Rect()
        handler = Handler(Looper.getMainLooper())
    }

    fun createWakeLock(): WakeLock {
        return delayedWakeLockFactory!!.create("AmbientIndication")
    }

    override fun dozeTimeTick() {
        updatePill()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (Dependency.get(StatusBarStateController::class.java) as StatusBarStateController).addCallback(this)
        (Dependency.get(NotificationMediaManager::class.java) as NotificationMediaManager).addCallback(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        (Dependency.get(StatusBarStateController::class.java) as StatusBarStateController).removeCallback(this)
        (Dependency.get(NotificationMediaManager::class.java) as NotificationMediaManager).removeCallback(this)
        mediaPlaybackState = 0
    }

    override fun onDozingChanged(isDozing: Boolean) {
        dozing = isDozing
        if (statusBarState == 1) {
            visibility = View.VISIBLE
        } else {
            visibility = View.INVISIBLE
        }
        if (this::textView.isInitialized) {
            textView.apply {
                isEnabled = !isDozing
                updateColors()
            }
        }
    }

    override fun onPrimaryMetadataOrStateChanged(mediaMetadata: MediaMetadata?, mediaState: Int) {
        if (mediaPlaybackState != mediaState) {
            mediaPlaybackState = mediaState
            if (NotificationMediaManager.isPlayingState(mediaState)) {
                setAmbientMusic(null, null, null, 0, false, null)
            }
        }
    }

    override fun onStateChanged(state: Int) {
        statusBarState = state
        if (state == 1) {
            visibility = View.VISIBLE
        } else {
            visibility = View.INVISIBLE
        }
    }

    fun onTextClick() {
        if (openIntent != null) {
            powerInteractor!!.wakeUpIfDozing("AMBIENT_MUSIC_CLICK", PowerManager.WAKE_REASON_GESTURE)
            if (ambientSkipUnlock) {
                sendBroadcastWithoutDismissingKeyguard(openIntent)
            } else {
                activityStarter!!.startPendingIntentDismissingKeyguard(openIntent)
            }
        }
    }

    fun setAmbientMusic(
      charSequence: CharSequence?,
      pendingIntent: PendingIntent?,
      pendingIntent2: PendingIntent?,
      i: Int,
      z: Boolean,
      str: String?
    ) {
        var drawable: Drawable?
        if (Objects.equals(ambientMusicText, charSequence) && Objects.equals(openIntent, pendingIntent) && Objects.equals(favoritingIntent, pendingIntent2) && iconOverride == i && Objects.equals(iconDescription, str) && ambientSkipUnlock == z) {
            return
        }
        ambientMusicText = charSequence
        openIntent = pendingIntent
        favoritingIntent = pendingIntent2
        ambientSkipUnlock = z
        iconOverride = i
        iconDescription = str
        val context = (this as FrameLayout).mContext
        drawable = when (i) {
            1 -> context.getDrawable(R.drawable.ic_music_search)
            2 -> null
            3 -> context.getDrawable(R.drawable.ic_music_not_found)
            4 -> context.getDrawable(R.drawable.ic_cloud_off)
            5 -> context.getDrawable(R.drawable.ic_favorite)
            6 -> context.getDrawable(R.drawable.ic_favorite_border)
            7 -> context.getDrawable(R.drawable.ic_error)
            8 -> context.getDrawable(R.drawable.ic_favorite_note)
            else -> null
        }
        ambientIconOverride = drawable
        updatePill()
    }

    fun updateColors() {
        textColorAnimator?.apply {
            if (isRunning) {
                cancel()
            }
        }
        val defaultColor = textView!!.textColors.defaultColor
        val dozeColor = if (dozing) -1 else textColor
        if (defaultColor == dozeColor) {
            textView!!.setTextColor(dozeColor)
            iconView!!.imageTintList = ColorStateList.valueOf(dozeColor)
            return
        }
        val ofArgb = ValueAnimator.ofArgb(defaultColor, dozeColor)
        textColorAnimator = ofArgb
        ofArgb.interpolator = Interpolators.LINEAR_OUT_SLOW_IN
        textColorAnimator!!.duration = 500L
        textColorAnimator!!.addUpdateListener { valueAnimator ->
            val intValue = (valueAnimator.animatedValue as Int).toInt()
            textView!!.setTextColor(intValue)
            iconView!!.imageTintList = ColorStateList.valueOf(intValue)
        }
        textColorAnimator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                textColorAnimator = null
            }
        })
        textColorAnimator!!.start()
    }

    fun updatePill() {
        var drawable: Drawable?
        var i = 0
        val textView = textView ?: return
        val i2 = indicationTextMode
        var z = true
        indicationTextMode = 1
        var charSequence: CharSequence? = ambientMusicText
        val z2 = textView.visibility == View.VISIBLE
        val charSequence2 = ambientMusicText
        var z3 = charSequence2 != null && charSequence2.length == 0
        this.textView!!.isClickable = openIntent != null
        iconView!!.isClickable = !((favoritingIntent == null && openIntent == null))
        var charSequence3: CharSequence? = if (TextUtils.isEmpty(iconDescription)) charSequence else iconDescription
        var drawable2: Drawable? = null
        if (!TextUtils.isEmpty(reverseChargingMessage)) {
            indicationTextMode = 2
            charSequence = reverseChargingMessage
            if (reverseChargingAnimation == null) {
                reverseChargingAnimation = (this as FrameLayout).mContext.getDrawable(R.anim.reverse_charging_animation)
            }
            val drawable3 = reverseChargingAnimation
            this.textView!!.isClickable = false
            iconView!!.isClickable = false
            charSequence3 = null
            drawable2 = drawable3
            z3 = false
        } else if (!TextUtils.isEmpty(wirelessChargingMessage)) {
            indicationTextMode = 3
            charSequence = wirelessChargingMessage
            this.textView!!.isClickable = false
            iconView!!.isClickable = false
            z3 = false
            charSequence3 = null
        } else if ((!TextUtils.isEmpty(charSequence) || z3) && ambientIconOverride.also { drawable2 = it } == null) {
            if (z2) {
                if (ambientMusicNoteIcon == null) {
                    ambientMusicNoteIcon = (this as FrameLayout).mContext.getDrawable(R.drawable.ic_music_note)
                }
                drawable2 = ambientMusicNoteIcon
            } else {
                if (ambientMusicAnimation == null) {
                    ambientMusicAnimation = (this as FrameLayout).mContext.getDrawable(R.anim.audioanim_animation)
                }
                drawable2 = ambientMusicAnimation
            }
        }
        this.textView!!.text = charSequence
        this.textView!!.contentDescription = charSequence
        iconView!!.contentDescription = charSequence3
        if (drawable2 != null) {
            iconBounds.set(0, 0, drawable2!!.intrinsicWidth, drawable2!!.intrinsicHeight)
            MathUtils.fitRect(iconBounds, if (drawable2 === ambientMusicNoteIcon) ambientMusicNoteIconIconSize else ambientIndicationIconSize)
            drawable = object : DrawableWrapper(drawable2) {
                override fun getIntrinsicHeight(): Int {
                    return this@AmbientIndicationContainer.iconBounds.height()
                }

                override fun getIntrinsicWidth(): Int {
                    return this@AmbientIndicationContainer.iconBounds.width()
                }
            }
            val i3 = if (!TextUtils.isEmpty(charSequence)) (resources.displayMetrics.density * 24.0f).toInt() else 0
            val textView2 = this.textView
            textView2!!.setPaddingRelative(textView2.paddingStart, this.textView!!.paddingTop, i3, this.textView!!.paddingBottom)
        } else {
            val textView3 = this.textView
            textView3!!.setPaddingRelative(textView3.paddingStart, this.textView!!.paddingTop, 0, this.textView!!.paddingBottom)
            drawable = drawable2
        }
        iconView!!.setImageDrawable(drawable)
        if (TextUtils.isEmpty(charSequence) && !z3) {
            z = false
        }
        val i4 = if (z) View.VISIBLE else View.GONE
        this.textView!!.visibility = i4
        if (drawable2 == null) {
            iconView!!.visibility = View.GONE
        } else {
            iconView!!.visibility = i4
        }
        if (!z) {
            this.textView!!.animate().cancel()
            if (drawable2 is AnimatedVectorDrawable) {
                (drawable2 as AnimatedVectorDrawable).reset()
            }
            handler.post(wakeLock!!.wrap {})
            return
        }
        if (!z2) {
            wakeLock!!.acquire("AmbientIndication")
            if (drawable2 is AnimatedVectorDrawable) {
                (drawable2 as AnimatedVectorDrawable).start()
            }
            this.textView!!.translationY = (height / 2).toFloat()
            this.textView!!.alpha = 0.0f
            this.textView!!.animate().alpha(1.0f).translationY(0.0f).setStartDelay(150L).setDuration(100L).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animator: Animator) {
                    when (0) {
                        0 -> {
                            this@AmbientIndicationContainer.wakeLock!!.release("AmbientIndication")
                            this@AmbientIndicationContainer.textView!!.animate().setListener(null)
                        }
                        else -> {
                            this@AmbientIndicationContainer.textColorAnimator = null
                        }
                    }
                }
            }).interpolator = Interpolators.DECELERATE_QUINT
            this.textView!!.animate().start()
            return
        }
        if (i2 == indicationTextMode) {
            handler.post(wakeLock!!.wrap {})
        } else {
            if (drawable2 == null || drawable2 !is AnimatedVectorDrawable) {
                return
            }
            wakeLock!!.acquire("AmbientIndication")
            (drawable2 as AnimatedVectorDrawable).start()
            wakeLock!!.release("AmbientIndication")
        }
    }
}
