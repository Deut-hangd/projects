package org.zero.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 *  Service 层的切面类, 用来织入输出日志语句
 */
@Aspect
@Slf4j
@Component
public class FilesServiceAspect {

    // 切入点: service 包下的 download 方法
    @Pointcut("execution(public * org.zero.service.FilesService.download(..))")
    public void downloadAspect() {}

    // 切入点: service 包下的 upload 方法
    @Pointcut("execution(public * org.zero.service.FilesService.upload(..))")
    public void uploadAspect() {}

    // 切入点: service 包下的 selectMetaInfo 方法
    @Pointcut("execution(public * org.zero.service.FilesService.selectMetaInfo(..))")
    public void selectMetaInfoAspect() {}

    // 将文件上传业务抛出的异常写入日志
    @AfterThrowing(pointcut = "downloadAspect()", throwing = "e")
    public void handleThrowing1(JoinPoint point, Exception e) {
        log.error("文件下载失败!", e);
        log.info(point.getSignature().getName() + "方法执行失败!");
    }

    // 将文件下载业务抛出的异常写入日志
    @AfterThrowing(pointcut = "uploadAspect()", throwing = "e")
    public void handleThrowing2(JoinPoint point, Exception e) {
        log.error("文件上传失败!", e);
        log.info(point.getSignature().getName() + "方法执行失败!");
    }

    // 将文件元信息获取业务抛出的异常写入日志
    @AfterThrowing(pointcut = "uploadAspect()", throwing = "e")
    public void handleThrowing3(JoinPoint point, Exception e) {
        log.error("文件元信息获取失败!", e);
        log.info(point.getSignature().getName() + "方法执行失败!");
    }

    // 在文件上传方法执行前后添加日志
    @Around("uploadAspect()")
    public Object aroundUpload(ProceedingJoinPoint point) throws Throwable {
        log.info("文件开始上传!");
        log.info(point.getSignature().getName() + "方法开始执行...");
        Object result = point.proceed();
        log.info(point.getSignature().getName() + "方法执行执行完成!");
        log.info("文件上传成功!");
        return result;
    }

    // 在文件下载方法执行前后添加日志
    @Around("downloadAspect()")
    public void aroundDownload(ProceedingJoinPoint point) throws Throwable {
        log.info("文件开始下载!");
        log.info(point.getSignature().getName() + "方法开始执行...");
        point.proceed();
        log.info(point.getSignature().getName() + "方法执行执行完成!");
        log.info("文件下载成功!");
    }

    // 在文件元信息获取方法执行前后添加日志
    @Around("selectMetaInfoAspect()")
    public Object aroundSelectMetaInfo(ProceedingJoinPoint point) throws Throwable {
        log.info("开始查询文件元信息!");
        log.info(point.getSignature().getName() + "方法开始执行...");
        Object result = point.proceed();
        log.info(point.getSignature().getName() + "方法执行执行完成!");
        log.info("文件元信息查询成功!");
        return result;
    }


}
