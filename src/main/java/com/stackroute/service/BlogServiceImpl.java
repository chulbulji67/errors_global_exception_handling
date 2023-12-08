package com.stackroute.service;

import com.stackroute.domain.Blog;
import com.stackroute.exception.BlogAlreadyExistsException;
import com.stackroute.exception.BlogNotFoundException;
import com.stackroute.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/* This is ServiceImplementation Class which should implement BlogService Interface and override all the implemented methods.
 * Handle suitable exceptions for all the implemented methods*/

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blog) {
        if (blogRepository.existsById(blog.getBlogId())) {
            throw new BlogAlreadyExistsException("Blog with ID " + blog.getBlogId() + " already exists");
        }

        // Save the blog
        return blogRepository.save(blog);
//        return blogRepository.save(blog);
//        return null;
    }

    @Override
    public List<Blog> getAllBlogs() {
        Iterable<Blog> blogsIterable = blogRepository.findAll();

        // Check if the result is null
        if (blogsIterable == null) {
            // Handle the case where findAll() returns null
            return  null; // or throw an exception, log a message, etc.
        }

        // Convert Iterable to List for iteration
        List<Blog> blogs = new ArrayList<>();
        blogsIterable.forEach(blogs::add);

        return blogs;
    }

    @Override
    public Blog getBlogById(int id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog with ID " + id + " not found"));
    }

    @Override
    public Blog deleteBlog(int id) {
        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException("Blog with ID " + id + " not found");
        }

        blogRepository.findById(id);
        Blog blog1 = blogRepository.findById(id).get();
        // Delete the blog
        blogRepository.deleteById(id);
        return blog1;
    }

    @Override
    public Blog updateBlog(Blog blog) {
        if (!blogRepository.existsById(blog.getBlogId())) {
            throw new BlogNotFoundException("Blog with ID " + blog.getBlogId() + " not found");
        }

        // Update the blog
        return blogRepository.save(blog);
    }
}

