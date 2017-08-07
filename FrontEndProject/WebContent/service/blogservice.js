/**
 * 
 */
app.factory('BlogService',function($http){
var blogService={}

blogService.saveBlog=function(blogpost){
return $http.post("http://localhost:8081/BackEndProject/saveblog",blogpost)
}
blogService.blogsApproved=function(){
	return $http.get("http://localhost:8081/BackEndProject/getallblogs/"+1)
	}
blogService.blogsWaitingForApproval=function(){
	return $http.get("http://localhost:8081/BackEndProject/getallblogs/"+0)
	}
blogService.getBlogPost=function(id){
	return $http.get("http://localhost:8081/BackEndProject/getblogpost/"+id)
	}
blogService.updateBlog=function(blogPost){
	return $http.put("http://localhost:8081/BackEndProject/updateblogpost",blogPost)
	}
blogService.addComment=function(blogComment){
	return $http.post("http://localhost:8081/BackEndProject/addblogcomment",blogComment)
	}
blogService.getBlogComments=function(blogId){
	return $http.get("http://localhost:8081/BackEndProject/getblogcomments/"+blogId)
	}
return blogService;
})
