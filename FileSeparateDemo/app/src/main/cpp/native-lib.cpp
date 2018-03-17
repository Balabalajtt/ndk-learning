#include "com_utte_fileseparatedemo_NDKFileUtils.h"
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>

long get_file_size( char *path){
    FILE *fp = fopen(path,"rb");
    fseek(fp,0,SEEK_END);
    return ftell(fp);

}

JNIEXPORT void JNICALL
Java_com_utte_fileseparatedemo_NDKFileUtils_diff(
        JNIEnv *env, jobject /* this */,
        jstring path_jstr, jstring path_pattern_jstr, jint file_num) {

    //文件路径
    const char* path = (*env).GetStringUTFChars(path_jstr, NULL);
    const char* path_pattern = (*env).GetStringUTFChars(path_pattern_jstr, NULL);

    //分配存放分文件路径指针的空间
    char** patches = (char **) malloc(sizeof(char*) * file_num);

    int i;
    for (i = 0; i < file_num; i++) {
        //分配存放分文件路径的空间 最长100字符
        patches[i] = (char *) malloc(sizeof(char) * 100);
        sprintf(patches[i], path_pattern, i + 1);//拼接
        __android_log_print(ANDROID_LOG_INFO, "patch path:", "%s", patches[i]);

    }

    //计算整文件大小
    long fileSize = get_file_size((char*)path);

    FILE *fpr = fopen(path, "rb");
    //整除文件个数
    if (fileSize % file_num == 0) {

        int part = (int) (fileSize / file_num);
        //一个一个边读边写
        for (i = 0; i < file_num; i++) {
            FILE* fpw = fopen(patches[i], "wb");
            int j;
            for (j = 0; j < part; j++) {
                int c = fgetc(fpr);
                fputc(c, fpw);
            }
            fclose(fpw);
        }
    } else {
        int part = (int) (fileSize / (file_num - 1));
        for (i = 0; i < file_num - 1; i++) {
            FILE* fpw = fopen(patches[i], "wb+");
            int j;
            for (j = 0; j < part; j++) {
                int c = fgetc(fpr);
                fputc(c, fpw);
            }
            fclose(fpw);
        }
        //不整除的最后一个文件
        FILE* fpw = fopen(patches[file_num - 1], "wb+");
        for (i = 0; i < fileSize % (file_num - 1); i++) {
            fputc(fgetc(fpr), fpw);
        }
        fclose(fpw);
    }
    fclose(fpr);

    //释放
    for (i = 0; i < file_num; i++) {
        free(patches[i]);
    }
    free(patches);
    (*env).ReleaseStringUTFChars(path_jstr, path);
    (*env).ReleaseStringUTFChars(path_pattern_jstr, path_pattern);

}

JNIEXPORT void JNICALL
Java_com_utte_fileseparatedemo_NDKFileUtils_patch (
        JNIEnv *env, jobject /* this */,
        jstring path_pattern_jstr, jint file_num, jstring merge_path_jstr) {

    const char *merge_path = (*env).GetStringUTFChars(merge_path_jstr, NULL);
    const char *path_pattern = (*env).GetStringUTFChars(path_pattern_jstr, NULL);

    //分割之后的子文件路径列表
    char **patches = (char **) malloc(sizeof(char *) * file_num);
    int i = 0;
    for (i = 0; i < file_num; i++) {
        patches[i] = (char *) malloc(sizeof(char) * 100);
        sprintf(patches[i], path_pattern, (i + 1));
        __android_log_print(ANDROID_LOG_INFO, "patch path:", "%s", patches[i]);
    }

    //对每个子文件读取写到merge文件
    FILE *fpw = fopen(merge_path, "wb");
    for (i = 0; i < file_num; i++) {
        long fileSize = get_file_size(patches[i]);
        FILE *fpr = fopen(patches[i], "rb");
        //每个字符
        int j;
        for (j = 0; j < fileSize; j++) {
            fputc(fgetc(fpr), fpw);
        }
        fclose(fpr);
    }
    fclose(fpw);

    //释放
    for (i = 0; i < file_num; i++) {
        free(patches[i]);
    }
    free(patches);
    (*env).ReleaseStringUTFChars(path_pattern_jstr, path_pattern);
    (*env).ReleaseStringUTFChars(merge_path_jstr, merge_path);

}

