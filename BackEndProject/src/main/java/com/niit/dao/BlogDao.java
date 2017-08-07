package com.niit.dao;

import java.util.List;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;

public interface BlogDao {
	void saveBlog(BlogPost blogPost);
	List<BlogPost> getAllBlogs(int approved);
	BlogPost getBlogById(int id);
	void updateBlogPost(BlogPost blogPost);
	void addComment(BlogComment blogComment);
	List<BlogComment> getBlogComments(int blogId);
}
