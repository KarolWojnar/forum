function fetchPosts(page, keyword, sort, direction) {
    fetch(`/posts?page=${page}&key-word=${encodeURIComponent(keyword)}&sort=${sort}&direction=${direction}`, {
        headers: { "X-Requested-With": "XMLHttpRequest" }
    })
        .then(response => response.text())
        .then(html => {
            const tempDiv = document.createElement("div");
            tempDiv.innerHTML = html;
            const fragment = tempDiv.querySelector("#postsContainer");
            if (fragment) {
                document.getElementById("postsContainer").innerHTML = fragment.innerHTML;
                attachPaginationListeners();
                attachPostListeners();
            }
        })
        .catch(err => console.error("Error fetching posts:", err));
}

function attachPaginationListeners() {
    document.querySelectorAll(".page-link").forEach(button => {
        button.addEventListener("click", function () {
            const page = this.getAttribute("data-page");
            const keyword = this.getAttribute("data-keyword") || "";
            const sort = sortBy.value;
            const direction = sortDirection.value;
            fetchPosts(page, keyword, sort, direction);
        });
    });
}

function attachSortListeners() {
    sortBy.addEventListener("change", function () {
        const keyword = searchInput.value.trim();
        const sort = sortBy.value;
        const direction = sortDirection.value;
        fetchPosts(0, keyword, sort, direction);
    });
    sortDirection.addEventListener("change", function () {
        const keyword = searchInput.value.trim();
        const sort = sortBy.value;
        const direction = sortDirection.value;
        fetchPosts(0, keyword, sort, direction);
    });
}

function attachPostListeners() {
    const postCards = document.querySelectorAll(".post-card");
    loadComments(postCards);

}

function closeExpanded() {
    expandedOverlay.classList.add("hidden");
    postsContainer.classList.remove("blurred");
    expandedOverlay.innerHTML = "";
}



function attachCommentListeners() {
    document.querySelectorAll("[id^='submitComment-']").forEach(button => {
        button.addEventListener("click", function () {
            const postCard = this.closest(".post-card");
            const postIdElem = postCard.querySelector("[id^='post-id-']");
            const commentContentElem = postCard.querySelector("[id^='commentContent-']");
            if (!postIdElem || !commentContentElem) return;
            const id = postIdElem.textContent;
            const commentContent = commentContentElem.value.trim();
            if (commentContent.length === 0) {
                alert("Comment cannot be empty.");
                return;
            }
            if (commentContent.length > 500) {
                alert("Comment cannot be longer than 500 characters.");
                return;
            }
            addComment(id, commentContent, null);
        });
    });
}

function loadComments(postCards) {
    postCards.forEach(card => {
        card.addEventListener("click", function () {
            const rect = card.getBoundingClientRect();
            const id = card.querySelector("[id^='post-id-']").textContent;
            const clone = card.cloneNode(true);
            clone.classList.remove("cursor-pointer", "hover:shadow-2xl");
            clone.classList.add("expanded-animation");
            clone.style.top = rect.top + "px";
            clone.style.left = rect.left + "px";
            clone.style.width = rect.width + "px";
            clone.style.height = rect.height + "px";
            const contentElem = clone.querySelector("[id^='post-content-']");
            const actionElem = clone.querySelector("[id^='post-action-']");
            const commentElem = clone.querySelector("[id^='new-comment-']");
            if (contentElem) {
                contentElem.classList.remove("hidden");
            }
            if (actionElem) {
                actionElem.classList.remove("hidden");
            }
            if (commentElem) {
                commentElem.classList.remove("hidden");
            }
            expandedOverlay.innerHTML = "";
            expandedOverlay.appendChild(clone);
            expandedOverlay.classList.remove("hidden");
            postsContainer.classList.add("blurred");
            requestAnimationFrame(() => {
                clone.style.top = "10%";
                clone.style.left = "10%";
                clone.style.width = "80%";
                clone.style.height = "80%";
            });

            fetch(`/posts/${id}`)
                .then(response => response.text())
                .then(html => {
                    const detailsContainer = document.createElement("div");
                    detailsContainer.innerHTML = html;
                    const commentsContainer = clone.querySelector(`[id^='commentsContainer-${id}']`);
                    if (commentsContainer) {
                        const newComments = detailsContainer.querySelector(".mt-6");
                        if (newComments) {
                            commentsContainer.innerHTML = newComments.innerHTML;
                        }
                    }
                    attachCommentListeners();
                })
                .catch(err => console.error("Error loading post details:", err));
        });
    });
}

function addComment(postId, commentContent, parentId) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    const params = new URLSearchParams();
    params.append("content", commentContent);
    if (parentId) {
        params.append("parentId", parentId);
    }

    fetch(`/posts/${postId}/comment`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
            "X-Requested-With": "XMLHttpRequest",
            [csrfHeader]: csrfToken
        },
        body: params.toString()
    })
        .then(response => {
            if (response.ok) {
                refreshComments(postId);
                const textarea = expandedOverlay.querySelector(`#commentContent-${postId}`);
                if (textarea) {
                    textarea.value = "";
                }
            } else {
                throw new Error(response.status.toString());
            }
        })
        .catch(error => {
            alert("Error adding comment: " + error.message);
        });
}

function fetchReplies(commentId, button) {
    fetch(`/comments/${commentId}/replies`, {
        headers: { "X-Requested-With": "XMLHttpRequest" }
    })
        .then(response => response.text())
        .then(html => {
            const repliesContainer = document.createElement("div");
            repliesContainer.innerHTML = html;
            const repliesList = repliesContainer.querySelector("ul");

            if (repliesList) {
                const commentBox = button.closest("li");
                const repliesPlaceholder = commentBox.querySelector(".replies-list");
                if (repliesPlaceholder) {
                    repliesPlaceholder.innerHTML = repliesList.innerHTML;
                }
                button.remove();
            }
        })
        .catch(err => console.error("Error loading replies:", err));
}


function refreshComments(postId) {
    fetch(`/posts/${postId}`)
        .then(response => response.text())
        .then(html => {
            const detailsContainer = document.createElement("div");
            detailsContainer.innerHTML = html;
            const expandedPost = expandedOverlay.querySelector(`[id^='post-card-${postId}']`);
            if (expandedPost) {
                const commentsContainer = expandedPost.querySelector(`[id^='commentsContainer-${postId}']`);
                if (commentsContainer) {
                    const newComments = detailsContainer.querySelector(".mt-6");
                    if (newComments) {
                        commentsContainer.innerHTML = newComments.innerHTML;
                    }
                }

                const commentCountElem = expandedPost.querySelector(".text-sm.text-gray-600 span:last-child");
                if (commentCountElem) {
                    const newCommentCount = detailsContainer.querySelector(".text-sm.text-gray-600 span:last-child")?.textContent;
                    if (newCommentCount) {
                        commentCountElem.textContent = newCommentCount;
                    }
                }
            }

            const postCard = document.getElementById(`post-card-${postId}`);
            if (postCard) {
                const commentCountElemMain = postCard.querySelector(".text-sm.text-gray-600 span:last-child");
                if (commentCountElemMain) {
                    const newCommentCount = detailsContainer.querySelector(".text-sm.text-gray-600 span:last-child")?.textContent;
                    if (newCommentCount) {
                        commentCountElemMain.textContent = newCommentCount;
                    }
                }
            }
        })
        .catch(err => console.error("Error loading comments:", err));
}

function dismissAlert(alerts) {
    alerts.forEach(function(alert) {
        setTimeout(function() {
            alert.style.transition = 'opacity 0.5s ease-in-out';
            alert.style.opacity = '0';

            setTimeout(function() {
                alert.remove();
            }, 500);
        }, 3000);
    });
}
