const template = document.createElement('template')

template.innerHTML = `<nav class="navbar navbar-expand-sm navbar-light text-white bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">
          <img
            src="./images/tvlogo.png"
            alt=""
            width="50"
            class="d-inline-block align-text-top"
          />
          <span class="text-white"> Tvservice</span>
        </a>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link active text-white" aria-current="page" href="/"
                >Home</a
              >
            </li>
            <li class="nav-item">
              <a class="nav-link text-white" href="/suggest.html">Suggest</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-white" href="/top.html">Top</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-white" href="/savedshows.html"
                >Saved Shows</a
              >
            </li>
          </ul>
          <a class="btn btn-danger nav-link text-white" href="/logout"
            >Logout</a
          >
        </div>
      </div>
    </nav>`

document.getElementById('navigation-bar').appendChild(template.content)
