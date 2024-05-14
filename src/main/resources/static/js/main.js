$(window).on('load', function() {
    var dateElements = document.querySelectorAll('.type-date');
    dateElements.forEach(function(elem) {
        var originalDate = elem.getAttribute('data-date');
        var date = new Date(originalDate);
        var options = { year: 'numeric', month: 'long', day: 'numeric' };
        var formattedDate = new Intl.DateTimeFormat('en-US', options).format(date);
        elem.textContent = elem.textContent + " - " + formattedDate;
    });

    const articles = document.querySelectorAll('.article-contents');

    articles.forEach(function(article) {
        const fullContent = article.getAttribute('data-content');
        article.innerHTML = truncateContent(fullContent, 120);
    });

    function truncateContent(content, maxLength) {
        let isTag = false, charCount = 0;
        let truncated = '';

        for (let i = 0; i < content.length; i++) {
            if (content[i] === "<") {
                isTag = true;
            } else if (content[i] === ">") {
                isTag = false;
            }

            if (!isTag) {
                charCount++;
            }

            truncated += content[i];

            if (charCount >= maxLength) {
                break;
            }
        }

        if (isTag) truncated += '>';
        truncated += '...';

        return truncated;
    }

    $('#submit').on('click', function() {
        var name = $('#name').val();
        var mail = $('#mail').val();
        var content = $('#content').val();
        var about = $('#about').val();

        var data = {
            content: content,
            mail: mail,
            fullName: name,
            about: about
        };

        fetch('/orbit/send-contact', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
            if (response.ok) {
                $('.send-response').css('display', 'block');
                setTimeout(() => {
                    $('.send-response').css('display', 'none');
                }, 5000);
            } else {
                $('.send-response-warning').css('display', 'block');
                setTimeout(() => {
                    $('.send-response-warning').css('display', 'none');
                }, 5000);
                throw new Error('Server responded with a non-OK status');
            }
        })
            .then(() => {
            clearInputs();
        })
            .catch(error => {
            console.error('Error:', error);
        });
    });

    function clearInputs(){
        $('#name').val('');
        $('#mail').val('');
        $('#content').val('');
        $('#about').val('');
    }

    $('.search-bar').on('keyup', function(event) {
        if (event.keyCode === 13) {
            var searchQuery = $('.search-bar').val().trim();
            if (searchQuery === "") {
                window.location.href = '/';
            }else {
                searchFunction(searchQuery);
            }
        }
    });


});

async function searchFunction(searchQuery) {

    try {
        const response = await fetch(`/find/search?query=${encodeURIComponent(searchQuery)}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Server responded with a non-OK status: ${response.status}`);
        }

        const data = await response.json();
        displayResults(data.data);
    } catch (error) {
        console.error('Error:', error);
        displayError(error);
    }
}

function displayResults(articles) {
    if (articles.length > 0){
        $('.context').css('display', 'none');
        const container = document.getElementById('articlesContainer');
        container.innerHTML = '';

        articles.forEach(article => {
            const item = document.createElement('div');
            item.className = 'item';
            item.setAttribute('data-id', article.id);

            item.innerHTML = `
            <a href="/article/${article.slug}">
                <img src="${article.url}" class="item-img">
                <div class="type-date" data-date="${article.createdAt}">${article.menuItem.title}</div>
                <div class="header">${article.title}</div>
                <div class="article-contents" data-content="${article.content}"></div>
            </a>
            <div class="author-inf">
                <div class="avatar">
                    <img class="avatar-img" src="${article.user.avatarUrl}">
                </div>
                <div class="info">
                    <p class="name">${article.user.userName}</p>
                    <p class="title">${article.user.title}</p>
                </div>
            </div>
        `;
            container.appendChild(item);
        });
    }else {
        $('.context').css('display', 'none');
        $('#no-result').css('display', 'block');
    }
}

function displayError(error) {
    $('#resultsContainer').html(`<div class="alert alert-danger">Error: ${error.message}</div>`);
}