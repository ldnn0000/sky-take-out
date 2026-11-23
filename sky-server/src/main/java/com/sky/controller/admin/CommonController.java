package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        // 上传文件到OSS
        String name = file.getOriginalFilename();
        String url = null;
        try {
            url = aliOssUtil.upload(file.getBytes(), name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 返回上传后的文件路径
        return Result.success(url);
    }
}