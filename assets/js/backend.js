// 后端服务模拟模块
var backend = {
  // API基础URL
  baseUrl: '/api',
  
  // 用户登录状态
  isLoggedIn: false,
  
  // 当前登录用户信息
  currentUser: null,
  
  // 模拟API请求延迟
  delay: 300,
  
  // 通用请求函数
  async request(endpoint, method = 'GET', data = null) {
    try {
      // 模拟网络延迟
      await new Promise(resolve => setTimeout(resolve, this.delay));
      
      // 构建URL
      const url = this.baseUrl + endpoint;
      
      // 构建请求选项
      const options = {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
      };
      
      // 添加请求体
      if (data) {
        options.body = JSON.stringify(data);
      }
      
      // 添加认证token
      if (this.isLoggedIn) {
        options.headers['Authorization'] = 'Bearer ' + this.getToken();
      }
      
      // 模拟请求成功
      console.log(`[模拟请求] ${method} ${url}`, data);
      
      // 根据不同端点返回模拟数据
      return this.mockResponse(endpoint, method, data);
    } catch (error) {
      console.error('请求错误:', error);
      throw error;
    }
  },
  
  // 模拟响应数据
  mockResponse(endpoint, method, data) {
    // 用户认证相关
    if (endpoint === '/login' && method === 'POST') {
      // 模拟登录成功
      this.isLoggedIn = true;
      this.currentUser = {
        id: '1',
        username: data.username,
        role: data.username.includes('admin') ? 'admin' : 
               data.username.includes('teacher') ? 'teacher' : 'student',
        name: data.username === 'student1' ? '张三' : 
              data.username === 'teacher1' ? '李老师' : '王管理员',
        avatar: '/assets/images/avatar.png'
      };
      
      // 存储用户信息
      localStorage.setItem('currentUser', JSON.stringify(this.currentUser));
      localStorage.setItem('isLoggedIn', 'true');
      
      return {
        success: true,
        data: {
          token: 'mock-jwt-token',
          user: this.currentUser
        }
      };
    }
    
    if (endpoint === '/logout' && method === 'POST') {
      // 模拟登出
      this.isLoggedIn = false;
      this.currentUser = null;
      localStorage.removeItem('currentUser');
      localStorage.removeItem('isLoggedIn');
      
      return { success: true };
    }
    
    // 课程相关
    if (endpoint === '/courses' && method === 'GET') {
      return {
        success: true,
        data: [
          { id: '1', name: '数学基础', teacher: '李老师', progress: 65, startDate: '2023-09-01' },
          { id: '2', name: '英语进阶', teacher: '张老师', progress: 42, startDate: '2023-09-15' },
          { id: '3', name: '计算机科学导论', teacher: '王老师', progress: 88, startDate: '2023-08-20' }
        ]
      };
    }
    
    // 作业相关
    if (endpoint === '/assignments' && method === 'GET') {
      return {
        success: true,
        data: [
          { id: '1', courseId: '1', title: '第一章习题', deadline: '2023-10-10', status: 'pending' },
          { id: '2', courseId: '2', title: '语法练习', deadline: '2023-10-15', status: 'completed' },
          { id: '3', courseId: '3', title: '编程作业', deadline: '2023-10-20', status: 'pending' }
        ]
      };
    }
    
    // 成绩相关
    if (endpoint === '/grades' && method === 'GET') {
      return {
        success: true,
        data: [
          { id: '1', courseId: '1', assignmentId: '1', score: 85, comment: '表现不错' },
          { id: '2', courseId: '2', assignmentId: '2', score: 92, comment: '优秀' },
          { id: '3', courseId: '3', assignmentId: '3', score: 78, comment: '继续努力' }
        ]
      };
    }
    
    // 默认返回
    return { success: true, data: [] };
  },
  
  // 获取认证token
  getToken() {
    return 'mock-jwt-token';
  },
  
  // 检查用户是否已登录
  checkLogin() {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const userData = localStorage.getItem('currentUser');
    
    if (isLoggedIn && userData) {
      this.isLoggedIn = true;
      this.currentUser = JSON.parse(userData);
    }
    
    return this.isLoggedIn;
  },
  
  // 获取当前登录用户
  getUser() {
    return this.currentUser;
  },
  
  // 判断用户是否已登录（作为函数）
  isLoggedIn() {
    return this.checkLogin(); // 使用checkLogin()方法来避免和属性重名的问题
  },
  
  // 获取当前登录用户（别名函数）
  getCurrentUser() {
    return this.getUser();
  }
};

// 检查登录状态函数 - 增强版，确保课程列表和智能答疑页面不重定向
function checkLoginStatus() {
  try {
    const isLoggedIn = backend.checkLogin();
    const currentUser = backend.getUser();
    
    // 根据登录状态和用户角色处理页面显示
    if (isLoggedIn && currentUser) {
      console.log('用户已登录:', currentUser);
      
      // 显示用户信息
      try {
        const userInfoElements = document.querySelectorAll('.user-info');
        userInfoElements.forEach(el => {
          el.textContent = currentUser.name || currentUser.username;
        });
      } catch (e) {}
      
      // 根据角色显示不同内容
      try {
        const roleBasedElements = document.querySelectorAll(`[data-role!="${currentUser.role}"]`);
        roleBasedElements.forEach(el => {
          if (el.hasAttribute('data-role')) {
            el.style.display = 'none';
          }
        });
      } catch (e) {}
      
      // 显示登录后的导航
      try {
        document.querySelectorAll('.logged-in-only').forEach(el => {
          el.style.display = '';
        });
      } catch (e) {}
      
      // 隐藏登录/注册按钮
      try {
        document.querySelectorAll('.not-logged-in-only').forEach(el => {
          el.style.display = 'none';
        });
      } catch (e) {}
    } else {
      console.log('用户未登录');
      
      // 隐藏登录后的导航
      try {
        document.querySelectorAll('.logged-in-only').forEach(el => {
          el.style.display = 'none';
        });
      } catch (e) {}
      
      // 显示登录/注册按钮
      try {
        document.querySelectorAll('.not-logged-in-only').forEach(el => {
          el.style.display = '';
        });
      } catch (e) {}
      
      // 课程列表和智能答疑机器人页面不执行重定向，其他页面才重定向
      try {
        const currentPath = window.location.pathname;
        
        // 允许访问的页面列表
        const allowedPages = ['login.html', 'register.html', 'courses.html', 'chatbot.html'];
        
        // 检查当前页面是否在允许的列表中
        const isAllowed = allowedPages.some(page => currentPath.includes(page));
        
        // 只有非允许页面才执行重定向
        if (!isAllowed) {
          // 不执行重定向，让这些页面保持原样
          console.log(`当前页面 ${currentPath} 不在允许的页面列表中，但不会执行重定向`);
        }
      } catch (e) {
        console.error('检查路径时发生错误:', e);
      }
    }
    
    return isLoggedIn;
  } catch (e) {
    console.error('检查登录状态时发生错误:', e);
    return false;
  }
}

// 登录函数
function login(username, password) {
  return backend.request('/login', 'POST', { username, password })
    .then(response => {
      if (response.success) {
        // 登录成功，刷新页面
        window.location.reload();
      }
      return response;
    })
    .catch(error => {
      console.error('登录失败:', error);
      return { success: false, message: '登录失败，请检查用户名和密码' };
    });
}

// 登出函数
function logout() {
  return backend.request('/logout', 'POST')
    .then(response => {
      if (response.success) {
        // 登出成功，跳转到登录页
        window.location.href = '/html/login.html';
      }
      return response;
    })
    .catch(error => {
      console.error('登出失败:', error);
      return { success: false };
    });
}

// 获取课程列表
function getCourses() {
  return backend.request('/courses', 'GET')
    .then(response => response.data || [])
    .catch(() => []);
}

// 获取作业列表
function getAssignments() {
  return backend.request('/assignments', 'GET')
    .then(response => response.data || [])
    .catch(() => []);
}

// 获取成绩列表
function getGrades() {
  return backend.request('/grades', 'GET')
    .then(response => response.data || [])
    .catch(() => []);
}