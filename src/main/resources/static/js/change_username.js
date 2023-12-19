    document.getElementById("changeUsernameForm").addEventListener("submit", function(event) {
        event.preventDefault();

        var currentUsername = document.getElementById("currentUsername").value;
        var newUsername = document.getElementById("newUsername").value;
        var password = document.getElementById("password").value;

        var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
        var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

        fetch('/api/users/' + currentUsername + '/change-username', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken, // Include the CSRF token in the header
                // Other headers as needed
            },
            body: 'newUsername=' + encodeURIComponent(newUsername) + '&password=' + encodeURIComponent(password)
        })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Failed to change username');
            }
            return response.text();
        })
        .then(function(data) {
            alert(data); // Show success message or handle it accordingly
        })
        .catch(function(error) {
            alert(error.message); // Show error message or handle it accordingly
        });
    });

