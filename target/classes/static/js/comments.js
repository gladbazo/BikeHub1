const offerId = document.getElementById('offerId').value;
const commentForm = document.getElementById('commentForm');
commentForm.addEventListener('submit', handleFormSubmission)

const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

const commentContainer = document.getElementById('commentContainer')

async function handleFormSubmission(event) {
    event.preventDefault();

    const messageValue = document.getElementById('message').value;

    fetch(`http://localhost:8080/api/${offerId}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accepts': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            message: messageValue
        })
    }).then(result => result.json())
        .then(data => {
            document.getElementById('message').value = ""
            commentContainer.innerHTML += commentAsHtml(data)
        })
}

function commentAsHtml(comment) {
    let commentHtml = '<div>\n'
    commentHtml += `<h4>${comment.authorName}</h4>\n`
    commentHtml += `<p>${comment.message}</p>\n`
    commentHtml += '</div>\n'

    return commentHtml
}

fetch(`http://localhost:8080/api/${offerId}/comments`, {
    headers: {
        'Accepts': 'application/json'
    }
}).then(result => result.json())
    .then(data => {
        for (let comment of data) {
            commentContainer.innerHTML += commentAsHtml(comment)
        }
    })