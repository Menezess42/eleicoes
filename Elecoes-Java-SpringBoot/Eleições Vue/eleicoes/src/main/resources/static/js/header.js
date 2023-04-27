const vnav = {
    data() {
        return{

        }
    },
    methods: {
        // listarPartidos() {
        //     Vue.createApp(v_partido).mount('#app');;
        //     Vue.createApp(vform_partido).mount('#appmodal');
        // },
        // listarCandidato() {
        //     Vue.createApp(v_cadidato).mount('#app')
        //     Vue.createApp(vform_candidato).mount('#appmodal')
        // },
        // listarCargo() {
        //     Vue.createApp(v_cargo).mount('#app')
        //     Vue.createApp(vform_cargo).mount('#appmodal')
        // },
        // listarEleicao() {
        //     Vue.createApp(v_eleicao).mount('#app')
        //     Vue.createApp(vform_eleicao).mount('#appmodal')
        // }
    },
    template:
        `
        <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
          <div class="container-fluid mx-5">
            <a class="navbar-brand" href="javascript:void(0)">
                <img src="logo_bandeira.png" alt="Bandeira do Brazil" width="45" height="45">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="mynavbar">
              <ul class="navbar-nav me-auto">
                <li class="nav-item">
                  <a class="nav-link" href="partidos.html">Partidos</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="candidato.html">Candidato</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="cargo.html">Cargo</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="eleicao.html">Eleição</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="votacao.html">Votação</a>
                </li>
              </ul>
              <form class="d-flex">
                <input class="form-control me-2" type="text" placeholder="Procurar...">
                <button class="btn btn-success" type="button">Procurar</button>
              </form>
            </div>
          </div>
        </nav>
    `
}
Vue.createApp(vnav).mount('#app_nav')
