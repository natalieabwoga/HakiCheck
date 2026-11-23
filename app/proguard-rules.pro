# HakiCheck ProGuard / R8 rules
-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-keep class ke.co.hakicheck.data.remote.model.** { *; }
-keep class ke.co.hakicheck.domain.model.** { *; }
