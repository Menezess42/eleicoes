// Função Ordenar
function compare(a, b) {
    if(a.nome.toLowerCase() < b.nome.toLowerCase())
        return -1;
    if(a.nome.toLowerCase() > b.nome.toLowerCase())
        return 1;
    return 0;
}

// --------------------------

// ------- // --------
// ---- PARTIDOS -----

const vform_partido = {
    data() {
        return {
            id: '0',
            nome: '',

            valorBotao: '',
            alterar: false,
        }
    },
    methods: {
        gravarPartido() {
            let myMethod = 'post';
            let myUrl = 'http://localhost:8080/apis/partido/incluir';
            if(this.alterar) {
                myUrl = 'http://localhost:8080/apis/partido/alterar';
                myMethod = 'put';
            }
            axios({
                method: myMethod,
                url: myUrl,
                timeout: 4000, // 4 segundos timeout
                data: {
                    id: this.id,
                    nome: this.nome
                }
            })
            .then( response => {
                    myModal.toggle();
                    partido_gerenciar.carregarTabela();
                }
            )
            .catch(error => console.error('timeout excedido'))
        },
        mudarValorBotao(valor) {
            this.valorBotao = valor
        },
        apagarForm() {
            this.id = '0';
            this.nome = '';
        }
    },
    template:
    `        
        <div class="modal" id="myModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Partido</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <!-- Aqui colocar um formulário para preencher os dados -->
                    <div class="container mt-1">
                        <form>
                            <div class="mb-3 mt-3">
                                <label for="id">ID:</label>
                                <input type="id" class="form-control" id="id" v-model="id" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="nome">Nome:</label>
                                <input type="text" class="form-control" id="nome" v-model="nome" placeholder="Informe nome do partido" >
                            </div>                  
                        </form>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button @click="gravarPartido" class="btn btn-success">{{this.valorBotao}}</button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                </div>

            </div>
        </div>
    </div>
    `
}

const v_partido = {
    data() { return { partidos: '', filtro: '' } },
    mounted() {
        this.carregarTabela();
    },
    methods: {
        carregarTabela() {
            // this.partidos = [{ "id": 1, "nome": "PPL" }];
            axios
            .get(`http://localhost:8080/apis/partido/buscar-todos?filtro=${this.filtro}`)
            .then(response => { this.partidos = response.data })
            .then(() => this.partidos.sort(compare))
            .catch(err => { this.info = err })

        },
        gravar() {
            this.apagarForm();
            partido_form.alterar = false;
            partido_form.mudarValorBotao('Cadastrar');
        },
        apagar(id) {
            if(confirm("Deseja Excluir?")) {
                axios({
                    method: 'delete',
                    url: `http://localhost:8080/apis/partido/${id}`,
                    timeout: 4000, // 4 segundos timeout
                })
                .then(response => partido_gerenciar.carregarTabela())
                .catch(error => console.error('timeout excedido'))
            }

        },
        alterar(id) {
            partido_form.mudarValorBotao('Alterar');
            myModal.toggle();
            axios
            .get(`http://localhost:8080/apis/partido/buscar-um?id=${id}`)
            .then(response => {
                let partido = response.data;
                partido_form.id = partido.id;
                partido_form.nome = partido.nome;
                partido_form.alterar = true;
            })
            .catch(err => { this.info = err })
        },
        apagarForm() {
            partido_form.apagarForm()
        }
    },
    template:
    `
        <h2 class="text-3xl text-center font-bold">Gerenciar Partidos</h2>  
                
        <div class="my-3 d-flex flex-column flex-sm-row align-content-center justify-content-center">
            <button type="button" @click="gravar" class="me-2 mt-1 btn btn-success" data-bs-toggle="modal" data-bs-target="#myModal">
                Novo Partido
            </button>
            <input type="text" @keyup="carregarTabela" class="form-control mt-1" id="filtro" v-model="filtro" placeholder="informe o filtro" >
        </div>
        
        <table class="table table-hover table-bordered">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Alterar</th>
              <th>Excluir</th>
            </tr>
          </thead>
          <tbody>
          <tr v-for="p in this.partidos">
              <td>{{p.nome}}</td>             
              <td @click="alterar(p.id)">
                <div class="d-flex justify-content-center">
                    <span class="bi bi-pencil"></span>
                </div>                
              </td>
              <td @click="apagar(p.id)">
                <div class="d-flex justify-content-center">
                    <span class="bi bi-trash"></span>
                </div>                    
              </td>      
          </tr>
          </tbody>
        </table> 
    `
};

const partido_gerenciar =  Vue.createApp(v_partido).mount('#app');
const partido_form = Vue.createApp(vform_partido).mount('#appmodal')

var myModal = new bootstrap.Modal(document.getElementById('myModal'), {
    keyboard: false
})
