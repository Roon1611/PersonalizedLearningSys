// 通用JavaScript功能

// 导航高亮
function setActiveNavItem() {
  const currentPath = window.location.pathname.split('/').pop();
  const navLinks = document.querySelectorAll('.nav-link, .sidebar-link');
  
  navLinks.forEach(link => {
    const linkPath = link.getAttribute('href');
    if (linkPath && linkPath.includes(currentPath)) {
      link.classList.add('active');
    }
  });
}

// 模态框功能
function initModals() {
  const modals = document.querySelectorAll('.modal');
  const modalTriggers = document.querySelectorAll('[data-modal]');
  const closeButtons = document.querySelectorAll('.modal-close');
  
  modalTriggers.forEach(trigger => {
    trigger.addEventListener('click', () => {
      const modalId = trigger.getAttribute('data-modal');
      const modal = document.getElementById(modalId);
      if (modal) {
        modal.classList.add('show');
        document.body.style.overflow = 'hidden';
      }
    });
  });
  
  closeButtons.forEach(button => {
    button.addEventListener('click', () => {
      const modal = button.closest('.modal');
      if (modal) {
        modal.classList.remove('show');
        document.body.style.overflow = '';
      }
    });
  });
  
  // 点击模态框外部关闭
  window.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal')) {
      e.target.classList.remove('show');
      document.body.style.overflow = '';
    }
  });
}

// 表单验证
function validateForm(form) {
  const inputs = form.querySelectorAll('input[required], select[required], textarea[required]');
  let isValid = true;
  
  inputs.forEach(input => {
    if (!input.value.trim()) {
      isValid = false;
      input.classList.add('border-danger');
      
      // 添加错误提示
      let errorElement = input.nextElementSibling;
      if (!errorElement || !errorElement.classList.contains('error-message')) {
        errorElement = document.createElement('div');
        errorElement.classList.add('error-message', 'text-danger', 'text-sm', 'mt-1');
        errorElement.textContent = '此字段为必填项';
        input.parentNode.appendChild(errorElement);
      }
    } else {
      input.classList.remove('border-danger');
      const errorElement = input.nextElementSibling;
      if (errorElement && errorElement.classList.contains('error-message')) {
        errorElement.remove();
      }
    }
  });
  
  return isValid;
}

// 侧边栏切换
function initSidebarToggle() {
  const toggleButton = document.querySelector('.sidebar-toggle');
  const sidebar = document.querySelector('.sidebar');
  const mainContent = document.querySelector('.main-content');
  
  if (toggleButton && sidebar && mainContent) {
    toggleButton.addEventListener('click', () => {
      sidebar.classList.toggle('collapsed');
      mainContent.classList.toggle('sidebar-collapsed');
    });
  }
}

// 进度条动画
function animateProgressBars() {
  const progressBars = document.querySelectorAll('.progress-bar[data-percent]');
  
  progressBars.forEach(bar => {
    const percent = bar.getAttribute('data-percent');
    setTimeout(() => {
      bar.style.width = percent + '%';
    }, 300);
  });
}

// 平滑滚动
function smoothScroll() {
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      
      const targetId = this.getAttribute('href');
      const targetElement = document.querySelector(targetId);
      
      if (targetElement) {
        window.scrollTo({
          top: targetElement.offsetTop - 80,
          behavior: 'smooth'
        });
      }
    });
  });
}

// 搜索功能
function initSearch() {
  const searchInputs = document.querySelectorAll('.search-input');
  
  searchInputs.forEach(input => {
    input.addEventListener('input', () => {
      const searchTerm = input.value.toLowerCase();
      const searchTarget = input.getAttribute('data-search-target');
      const items = document.querySelectorAll(`.${searchTarget}`);
      
      items.forEach(item => {
        const text = item.textContent.toLowerCase();
        if (text.includes(searchTerm)) {
          item.style.display = '';
        } else {
          item.style.display = 'none';
        }
      });
    });
  });
}

// 选项卡功能
function initTabs() {
  const tabContainers = document.querySelectorAll('.tab-container');
  
  tabContainers.forEach(container => {
    const tabButtons = container.querySelectorAll('.tab-button');
    const tabContents = container.querySelectorAll('.tab-content');
    
    tabButtons.forEach(button => {
      button.addEventListener('click', () => {
        const tabId = button.getAttribute('data-tab');
        
        // 移除所有活动状态
        tabButtons.forEach(btn => btn.classList.remove('active'));
        tabContents.forEach(content => content.classList.remove('active'));
        
        // 添加活动状态
        button.classList.add('active');
        const activeContent = container.querySelector(`#${tabId}`);
        if (activeContent) {
          activeContent.classList.add('active');
        }
      });
    });
  });
}

// 数据图表（模拟）
function initCharts() {
  // 这里可以根据实际需求集成Chart.js等图表库
  const chartContainers = document.querySelectorAll('.chart-container');
  
  chartContainers.forEach(container => {
    // 模拟图表数据展示
    const chartType = container.getAttribute('data-chart-type');
    
    if (chartType === 'line') {
      // 简单的模拟折线图
      const canvas = document.createElement('canvas');
      container.appendChild(canvas);
      
      // 这里可以添加实际的图表绘制逻辑
      const ctx = canvas.getContext('2d');
      ctx.strokeStyle = '#4361ee';
      ctx.lineWidth = 2;
      ctx.beginPath();
      ctx.moveTo(10, 100);
      ctx.lineTo(50, 50);
      ctx.lineTo(100, 80);
      ctx.lineTo(150, 30);
      ctx.lineTo(200, 90);
      ctx.stroke();
    } else if (chartType === 'bar') {
      // 简单的模拟柱状图
      const canvas = document.createElement('canvas');
      container.appendChild(canvas);
      
      const ctx = canvas.getContext('2d');
      ctx.fillStyle = '#4361ee';
      ctx.fillRect(10, 50, 30, 100);
      ctx.fillRect(50, 80, 30, 70);
      ctx.fillRect(90, 30, 30, 120);
      ctx.fillRect(130, 60, 30, 90);
      ctx.fillRect(170, 40, 30, 110);
    } else if (chartType === 'pie') {
      // 简单的模拟饼图
      const canvas = document.createElement('canvas');
      container.appendChild(canvas);
      
      const ctx = canvas.getContext('2d');
      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;
      const radius = 50;
      
      // 绘制饼图的几个部分
      ctx.fillStyle = '#4361ee';
      ctx.beginPath();
      ctx.moveTo(centerX, centerY);
      ctx.arc(centerX, centerY, radius, 0, Math.PI * 0.4);
      ctx.closePath();
      ctx.fill();
      
      ctx.fillStyle = '#4cc9f0';
      ctx.beginPath();
      ctx.moveTo(centerX, centerY);
      ctx.arc(centerX, centerY, radius, Math.PI * 0.4, Math.PI * 0.8);
      ctx.closePath();
      ctx.fill();
      
      ctx.fillStyle = '#f72585';
      ctx.beginPath();
      ctx.moveTo(centerX, centerY);
      ctx.arc(centerX, centerY, radius, Math.PI * 0.8, Math.PI * 1.6);
      ctx.closePath();
      ctx.fill();
      
      ctx.fillStyle = '#ff0054';
      ctx.beginPath();
      ctx.moveTo(centerX, centerY);
      ctx.arc(centerX, centerY, radius, Math.PI * 1.6, Math.PI * 2);
      ctx.closePath();
      ctx.fill();
    }
  });
}

// 模拟机器学习功能
function initMachineLearningFeatures() {
  // 学习路径推荐（模拟）
  const recommendationButtons = document.querySelectorAll('.recommendation-button');
  
  recommendationButtons.forEach(button => {
    button.addEventListener('click', () => {
      const courseId = button.getAttribute('data-course-id');
      const modal = document.getElementById('recommendation-modal');
      
      if (modal) {
        // 模拟加载推荐内容
        const modalBody = modal.querySelector('.modal-body');
        modalBody.innerHTML = '<div class="text-center py-8"><div class="loader mx-auto mb-4"></div><p>正在生成个性化学习路径...</p></div>';
        
        modal.classList.add('show');
        document.body.style.overflow = 'hidden';
        
        // 模拟API请求延迟
        setTimeout(() => {
          modalBody.innerHTML = `
            <h3 class="text-lg font-semibold mb-4">个性化学习路径推荐</h3>
            <div class="space-y-4">
              <div class="p-4 border border-gray-200 rounded-lg bg-blue-50">
                <div class="flex items-center">
                  <span class="bg-blue-500 text-white w-8 h-8 rounded-full flex items-center justify-center mr-3">1</span>
                  <div>
                    <h4 class="font-medium">第1步：基础知识巩固</h4>
                    <p class="text-sm text-gray-600">建议先复习代数基础概念，这是您的薄弱环节</p>
                  </div>
                </div>
              </div>
              <div class="p-4 border border-gray-200 rounded-lg">
                <div class="flex items-center">
                  <span class="bg-gray-300 text-gray-700 w-8 h-8 rounded-full flex items-center justify-center mr-3">2</span>
                  <div>
                    <h4 class="font-medium">第2步：进阶应用练习</h4>
                    <p class="text-sm text-gray-600">完成相关练习，巩固知识点</p>
                  </div>
                </div>
              </div>
              <div class="p-4 border border-gray-200 rounded-lg">
                <div class="flex items-center">
                  <span class="bg-gray-300 text-gray-700 w-8 h-8 rounded-full flex items-center justify-center mr-3">3</span>
                  <div>
                    <h4 class="font-medium">第3步：综合测试评估</h4>
                    <p class="text-sm text-gray-600">进行模拟测试，检验学习成果</p>
                  </div>
                </div>
              </div>
            </div>
            <div class="mt-6">
              <p class="text-sm text-gray-500 italic">* 此推荐基于您的学习数据和测验表现生成</p>
            </div>
          `;
        }, 1500);
      }
    });
  });
  
  // 学习风险预测（模拟）
  const riskButtons = document.querySelectorAll('.risk-prediction-button');
  
  riskButtons.forEach(button => {
    button.addEventListener('click', () => {
      const studentId = button.getAttribute('data-student-id');
      const modal = document.getElementById('risk-modal');
      
      if (modal) {
        const modalBody = modal.querySelector('.modal-body');
        modalBody.innerHTML = '<div class="text-center py-8"><div class="loader mx-auto mb-4"></div><p>正在分析学习风险...</p></div>';
        
        modal.classList.add('show');
        document.body.style.overflow = 'hidden';
        
        setTimeout(() => {
          modalBody.innerHTML = `
            <h3 class="text-lg font-semibold mb-4">学习风险预测</h3>
            <div class="mb-6">
              <div class="flex justify-between mb-1">
                <span>挂科风险指数</span>
                <span class="font-medium">35%</span>
              </div>
              <div class="progress">
                <div class="progress-bar bg-yellow-500" style="width: 35%"></div>
              </div>
              <p class="text-sm text-gray-500 mt-2">中等风险：建议增加学习时间，及时完成作业</p>
            </div>
            
            <div class="space-y-4">
              <h4 class="font-medium">风险因素分析</h4>
              <div class="flex items-start space-x-3">
                <i class="fas fa-exclamation-triangle text-yellow-500 mt-1"></i>
                <p class="text-sm">视频观看完成率较低（仅65%）</p>
              </div>
              <div class="flex items-start space-x-3">
                <i class="fas fa-exclamation-triangle text-yellow-500 mt-1"></i>
                <p class="text-sm">最近两次测验成绩呈下降趋势</p>
              </div>
              <div class="flex items-start space-x-3">
                <i class="fas fa-check-circle text-green-500 mt-1"></i>
                <p class="text-sm">作业提交及时性良好</p>
              </div>
            </div>
            
            <div class="mt-6">
              <h4 class="font-medium mb-2">推荐辅导资源</h4>
              <ul class="space-y-2">
                <li class="flex items-center space-x-2">
                  <i class="fas fa-book text-blue-500"></i>
                  <a href="#" class="text-sm text-blue-500 hover:underline">《高等数学基础巩固指南》</a>
                </li>
                <li class="flex items-center space-x-2">
                  <i class="fas fa-video text-blue-500"></i>
                  <a href="#" class="text-sm text-blue-500 hover:underline">专题辅导视频：代数重点解析</a>
                </li>
                <li class="flex items-center space-x-2">
                  <i class="fas fa-users text-blue-500"></i>
                  <a href="#" class="text-sm text-blue-500 hover:underline">加入学习小组：周三19:00-21:00</a>
                </li>
              </ul>
            </div>
          `;
        }, 1500);
      }
    });
  });
  
  // 智能答疑机器人（模拟）
  const chatButtons = document.querySelectorAll('.chat-bot-button');
  
  chatButtons.forEach(button => {
    button.addEventListener('click', () => {
      const modal = document.getElementById('chat-bot-modal');
      
      if (modal) {
        const chatContainer = modal.querySelector('.chat-container');
        const messageInput = modal.querySelector('.chat-input');
        const sendButton = modal.querySelector('.chat-send');
        
        // 清空聊天内容
        chatContainer.innerHTML = '';
        
        // 添加欢迎消息
        addChatMessage(chatContainer, 'bot', '你好！我是智能学习助手，请问有什么可以帮助你的？');
        
        modal.classList.add('show');
        document.body.style.overflow = 'hidden';
        
        // 发送消息功能
        function sendMessage() {
          const message = messageInput.value.trim();
          if (message) {
            addChatMessage(chatContainer, 'user', message);
            messageInput.value = '';
            
            // 模拟机器人回复
            setTimeout(() => {
              let response = '';
              if (message.includes('解题') || message.includes('怎么做')) {
                response = '这道题主要考察了函数的连续性和极限的计算方法。首先，你需要回忆一下连续性的定义，然后应用极限的四则运算法则来求解。如果你还是不清楚，可以查看教材第125页的相关例题，或者观看课程视频的第35分钟部分。';
              } else if (message.includes('作业') || message.includes('提交')) {
                response = '下一次作业提交截止时间是下周五晚上23:59，请确保按时完成。本次作业主要覆盖了我们最近学习的微分方程部分，建议先复习相关知识点再开始做题。';
              } else if (message.includes('考试') || message.includes('测验')) {
                response = '下一次测验将于下周三上午10:00-11:30进行，主要考察前四章的内容。建议重点复习导数的应用和积分计算部分，这两部分占分比例较大。';
              } else {
                response = '感谢你的提问！这个问题比较复杂，我建议你可以参考教材的相关章节，或者向老师请教。如果你需要更详细的解答，请提供更多的背景信息。';
              }
              addChatMessage(chatContainer, 'bot', response);
              
              // 滚动到底部
              chatContainer.scrollTop = chatContainer.scrollHeight;
            }, 1000);
            
            // 滚动到底部
            chatContainer.scrollTop = chatContainer.scrollHeight;
          }
        }
        
        // 点击发送按钮
        sendButton.addEventListener('click', sendMessage);
        
        // 按Enter键发送
        messageInput.addEventListener('keypress', (e) => {
          if (e.key === 'Enter') {
            sendMessage();
          }
        });
      }
    });
  });
  
  // 添加聊天消息
  function addChatMessage(container, sender, message) {
    const messageElement = document.createElement('div');
    messageElement.classList.add('chat-message', sender);
    
    const contentClass = sender === 'user' ? 'bg-blue-500 text-white' : 'bg-gray-200';
    const alignClass = sender === 'user' ? 'ml-auto' : 'mr-auto';
    
    messageElement.innerHTML = `
      <div class="flex items-start mb-3">
        <div class="w-8 h-8 rounded-full flex items-center justify-center bg-gray-300 mr-2">
          <i class="fas ${sender === 'user' ? 'fa-user' : 'fa-robot'}"></i>
        </div>
        <div class="max-w-xs ${alignClass} p-2 rounded-lg ${contentClass}">
          <p>${message}</p>
        </div>
      </div>
    `;
    
    container.appendChild(messageElement);
  }
}

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', () => {
  setActiveNavItem();
  initModals();
  initSidebarToggle();
  animateProgressBars();
  smoothScroll();
  initSearch();
  initTabs();
  initCharts();
  initMachineLearningFeatures();
  
  // 为所有表单添加验证
  const forms = document.querySelectorAll('form');
  forms.forEach(form => {
    form.addEventListener('submit', (e) => {
      if (!validateForm(form)) {
        e.preventDefault();
      }
    });
  });
  
  // 模拟数据加载
  const loaders = document.querySelectorAll('.data-loader');
  loaders.forEach(loader => {
    setTimeout(() => {
      loader.classList.add('hidden');
      const content = loader.nextElementSibling;
      if (content) {
        content.classList.remove('hidden');
        content.classList.add('fade-in');
      }
    }, 800);
  });
});