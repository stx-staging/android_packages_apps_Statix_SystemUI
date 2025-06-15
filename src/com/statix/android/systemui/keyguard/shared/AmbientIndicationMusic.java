package com.google.android.systemui.keyguard.shared;

import android.app.PendingIntent;
import kotlin.jvm.internal.Intrinsics;

public final class AmbientIndicationMusic {
    public final PendingIntent favoritingIntent;
    public final String iconDescription;
    public final Integer iconOverride;
    public final PendingIntent openIntent;
    public final Boolean skipUnlock;
    public final CharSequence text;

    public AmbientIndicationMusic(CharSequence charSequence, PendingIntent pendingIntent, PendingIntent pendingIntent2, Integer num, Boolean bool, String str) {
        this.text = charSequence;
        this.openIntent = pendingIntent;
        this.favoritingIntent = pendingIntent2;
        this.iconOverride = num;
        this.skipUnlock = bool;
        this.iconDescription = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AmbientIndicationMusic)) {
            return false;
        }
        AmbientIndicationMusic ambientIndicationMusic = (AmbientIndicationMusic) obj;
        return Intrinsics.areEqual(this.text, ambientIndicationMusic.text) && Intrinsics.areEqual(this.openIntent, ambientIndicationMusic.openIntent) && Intrinsics.areEqual(this.favoritingIntent, ambientIndicationMusic.favoritingIntent) && Intrinsics.areEqual(this.iconOverride, ambientIndicationMusic.iconOverride) && Intrinsics.areEqual(this.skipUnlock, ambientIndicationMusic.skipUnlock) && Intrinsics.areEqual(this.iconDescription, ambientIndicationMusic.iconDescription);
    }

    public final int hashCode() {
        CharSequence charSequence = this.text;
        int hashCode = (charSequence == null ? 0 : charSequence.hashCode()) * 31;
        PendingIntent pendingIntent = this.openIntent;
        int hashCode2 = (hashCode + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        PendingIntent pendingIntent2 = this.favoritingIntent;
        int hashCode3 = (hashCode2 + (pendingIntent2 == null ? 0 : pendingIntent2.hashCode())) * 31;
        Integer num = this.iconOverride;
        int hashCode4 = (hashCode3 + (num == null ? 0 : num.hashCode())) * 31;
        Boolean bool = this.skipUnlock;
        int hashCode5 = (hashCode4 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str = this.iconDescription;
        return hashCode5 + (str != null ? str.hashCode() : 0);
    }

    public final String toString() {
        CharSequence charSequence = this.text;
        return "AmbientIndicationMusic(text=" + ((Object) charSequence) + ", openIntent=" + this.openIntent + ", favoritingIntent=" + this.favoritingIntent + ", iconOverride=" + this.iconOverride + ", skipUnlock=" + this.skipUnlock + ", iconDescription=" + this.iconDescription + ")";
    }
}
