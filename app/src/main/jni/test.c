//
// Created by Marvin on 2019/3/25.
//
#include "jni.h"
#include <android/log.h>
#include "com_example_mysdk_MyJni.h"
#define  LOG_TAG    "JNILog==================================================================="
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
JNIEXPORT jstring JNICALL Java_com_example_mysdk_MyJni_getString
  (JNIEnv *env, jclass jz){

  return (*env)->NewStringUTF(env,"this text from SO");

  }
void _init()
{
char * name = "_init_";
LOGD("test for  %s\n", name );
}
 void func()
 {
  __asm__(".space 0x1111, 1");
  __asm__(".space 0x1110, 1");
 }
  void func2()
 {
  __asm__(".space 0x1111, 1");
  __asm__(".space 0x1110, 1");
 }
  void func3()
 {
  __asm__(".space 0x1111, 1");
  __asm__(".space 0x1110, 1");
 }
  void func4()
 {
  __asm__(".space 0x1111, 1");
  __asm__(".space 0x1110, 1");
 }
  void func5()
 {
  __asm__(".space 0x1111, 1");
  __asm__(".space 0x1110, 1");
 }
  void func6()
 {
  __asm__(".space 0x1111, 1");
  __asm__(".space 0x1110, 1");
 }
