#########################
### Project resources ###
#########################

-keep class com.coveracademy.api.model.** { *; }
-keep class com.coveracademy.api.enumeration.** { *; }
-keep class com.coveracademy.api.exception.APIException { *; }

-keepclassmembers class com.coveracademy.api.model.** implements android.os.Parcelable {
  static ** CREATOR;
}
-keepclassmembers class com.coveracademy.api.model.** implements java.io.Serializable {
  static final long serialVersionUID;
  private static final java.io.ObjectStreamField[] serialPersistentFields;
  !static !transient <fields>;
  !private <fields>;
  !private <methods>;
  private void writeObject(java.io.ObjectOutputStream);
  private void readObject(java.io.ObjectInputStream);
  java.lang.Object writeReplace();
  java.lang.Object readResolve();
}

################################
### Improve reverse engineer ###
################################

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

###########################
### Direct dependencies ###
###########################

# Butter Knife
-dontwarn butterknife.internal.**
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
  @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
  @butterknife.* <methods>;
}

# Google services
-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.** { *; }

# Gson
-keep class sun.misc.Unsafe { *; }
-keepattributes Signature
-keepattributes *Annotation*

# New Relic
-keep class com.newrelic.** { *; }
-dontwarn com.newrelic.**
-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable

# Volley
-dontwarn com.android.volley.**

#############################
### Indirect dependencies ###
#############################

# Okio
-dontwarn okio.**

# OkHttp
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

# Slf4j
-dontwarn org.slf4j.**