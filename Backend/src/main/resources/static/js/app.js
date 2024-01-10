const apiUrl = 'http://localhost:8080/tasks';  // Replace with your API URL

async function createTask() {
    const taskTime = document.getElementById('taskTime').value;
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa('username:password')  // Replace with actual credentials
        },
        body: JSON.stringify({ time: taskTime })
    });

    if (response.ok) {
        console.log('Task created');
        loadTasks();
    } else {
        console.error('Error creating task');
    }
}

async function loadTasks() {
    const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Authorization': 'Basic ' + btoa('username:password')  // Replace with actual credentials
        }
    });

    if (response.ok) {
        const tasks = await response.json();
        const tasksList = document.getElementById('tasksList');
        tasksList.innerHTML = '';
        tasks.forEach(task => {
            const listItem = document.createElement('li');
            listItem.textContent = `Task Time: ${task.time}`;
            tasksList.appendChild(listItem);
        });
    } else {
        console.error('Error loading tasks');
    }
}

loadTasks();
