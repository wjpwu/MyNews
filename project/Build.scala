import sbt._

import Keys._
import AndroidKeys._
import AndroidNdkKeys._

object General {
  // Some basic configuration
  val settings = Defaults.defaultSettings ++ Seq(
    name := "HoneyT",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.10.1",
    platformName in Android := "android-17",
    compileOrder := CompileOrder.JavaThenScala,
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6"))

  // Default Proguard settings
  lazy val proguardSettings = inConfig(Android)(Seq(
    useProguard := true,
    proguardOptimizations += 
      "-keep class com.honey.cc.** { *; } " +
      "-keep class com.actionbarsherlock.** {*;} " +
      "-keep class eu.erikw.** {*;} " +
      "-verbose  " +
      "-dontobfuscate  " +
      "-dontoptimize  " +
      "-printseeds target/keep.log  " +
      "-printmapping target/obf.log  " +
      "-keepparameternames  " +
      "-dontskipnonpubliclibraryclasses  " +
      "-dontskipnonpubliclibraryclassmembers  " +
      "-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod  " +
      "-keepclassmembers class * { ** MODULE$; }  " +
      "-keepdirectories  " +
      "-keep class scala.runtime.ObjectRef " +
      "-keep class scala.runtime.VolatileObjectRef " +
      "-keep class scala.reflect.Manifest " +
      "-keep class scala.reflect.ClassTag " +
      "-keep class scala.reflect.ClassManifestDeprecatedApis* " +
      "-keep class scala.collection.mutable.ArrayBuffer " +
      "-keep class scala.math.Ordering " +
      "-keep class org.scaloid.common.SActivity " +
      "-keep class org.scaloid.common.SContext " +
      "-keep class org.scaloid.common.Registerable " +
      "-keep class org.scaloid.common.LoggerTag " +
      "-keep class android.support.v4.app.Fragment " +
      "-keep public class scala.Option " +
      "-keep public class scala.PartialFunction " +
      "-keep public class scala.Function0 " +
      "-keep public class scala.Function1 " +
      "-keep public class scala.Function2 " +
      "-keep public class scala.Product " +
      "-keep public class scala.Tuple2 " +
      "-keep public class scala.collection.Seq " +
      "-keep public class scala.collection.GenSeq " +
      "-keep public class scala.collection.immutable.List " +
      "-keep public class scala.collection.immutable.Map " +
      "-keep public class scala.collection.SeqLike {public protected *;} " +
      "-keep public class * extends android.app.Activity " +
      "-keep public class * extends android.app.Application " +
      "-keep public class * extends android.app.Service " +
      "-keep public class * extends android.app.backup.BackupAgentHelper " +
      "-keep public class * extends android.appwidget.AppWidgetProvider " +
      "-keep public class * extends android.content.BroadcastReceiver " +
      "-keep public class * extends android.content.ContentProvider " +
      "-keep public class * extends android.preference.Preference " +
      "-keep public class * extends android.view.View " +
      "-keepclasseswithmembernames class * {native <methods>;} " +
      "-keepclasseswithmembers class * {public <init>(android.content.Context, android.util.AttributeSet);} " +
      "-keepclasseswithmembers class * {public <init>(android.content.Context, android.util.AttributeSet, int);} " +
      "-keepclassmembers class * extends android.app.Activity {   public void *(android.view.View);} " +
      "-keepclassmembers enum * {public static **[] values();public static ** valueOf(java.lang.String);} " +
      "-keep class * implements android.os.Parcelable {  public static final android.os.Parcelable$Creator *;}"
      
      ))

  // Example NDK settings
  lazy val ndkSettings = AndroidNdk.settings ++ inConfig(Android)(Seq(
    jniClasses := Seq(),
    javahOutputFile := Some(new File("native.h"))))

  // Full Android settings
  lazy val fullAndroidSettings =
    General.settings ++
      AndroidProject.androidSettings ++
      TypedResources.settings ++
      proguardSettings ++
      AndroidManifestGenerator.settings ++
      AndroidMarketPublish.settings ++ Seq(
        keyalias in Android := "honey",
        //      libraryDependencies += "com.google.android" % "support-v4" % "r7",
        libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test",
        libraryDependencies += "org.scaloid" %% "scaloid" % "2.2-8", libraryDependencies += "com.actionbarsherlock" % "actionbarsherlock" % "4.3.1", libraryDependencies += "com.github.chrisbanes.pulltorefresh" % "library" % "2.2.1" from "file:/Users/Aaron/Dev/sbt_ll/fxthomas/pulltorefresh2.2.1.apklib", libraryDependencies += "ew.erikw" % "honeypull" % "1.3.0" from "file:/Users/Aaron/Dev/sbt_ll/fxthomas/honeypull.apklib")
}

object AndroidBuild extends Build {
  lazy val main = Project(
    "main",
    file("."),
    settings = General.fullAndroidSettings ++ AndroidEclipseDefaults.settings)

  lazy val tests = Project(
    "tests",
    file("tests"),
    settings = General.settings ++
      AndroidEclipseDefaults.settings ++
      AndroidTest.androidSettings ++
      General.proguardSettings ++ Seq(
        name := "HoneyTTests")) dependsOn main
}
