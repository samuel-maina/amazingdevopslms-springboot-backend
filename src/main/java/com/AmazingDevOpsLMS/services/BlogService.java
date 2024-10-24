/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.model.ArticleHeaderImage;
import com.AmazingDevOpsLMS.model.Blog;
import com.AmazingDevOpsLMS.repositories.ArticleHeaderImageRepository;
import com.AmazingDevOpsLMS.repositories.BlogRepository;
import com.AmazingDevOpsLMS.repositories.NotificationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ArticleHeaderImageRepository headerImageRepository;

    public Optional<Blog> findBlogById(String id) {
        return blogRepository.findById(id);
    }

    public Blog save(Blog blog) {
        blog.setId(RandomString.make(64));
        blog.setDate(LocalDate.now());
        Blog temp = blogRepository.save(blog);
        notificationService.save(temp);
        temp.setAbout("");
        temp.setBody("");
        return temp;
    }

    public Iterable<Blog> blogs() {
        return blogRepository.findAll();
    }

    public void saveImageHeaderUrls(String ArticleId, ArticleHeaderImage headerImage) {
        Blog blog = this.findBlogById(ArticleId).get();
        headerImage.setBlog(blog);
        headerImage.setId(RandomString.make(10));
        headerImageRepository.save(headerImage);
    }

}
