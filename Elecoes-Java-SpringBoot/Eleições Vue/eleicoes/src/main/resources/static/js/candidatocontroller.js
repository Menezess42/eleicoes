// Função Ordenar
function compare(a, b) {
    if(a.nome.toLowerCase() < b.nome.toLowerCase())
        return -1;
    if(a.nome.toLowerCase() > b.nome.toLowerCase())
        return 1;
    return 0;
}

const vform_candidato = {
    data() {
        return {
            id: '0',
            nome: '',
            numero: '',
            partido_id: '',
            cargos: '',
            votos: '',

            novo_cargo: '',
            lista_partidos: '',
            valorBotao: '',
            alterar: false
        }
    },
    mounted() {
        axios.get('http://localhost:8080/apis/partido/buscar-todos?filtro')
        .then(response => {
            this.lista_partidos = response.data;
        })
    },
    methods: {
        buscarPartido() {
            pos = 0;
            while(pos < this.lista_partidos.length && this.partido_id != this.lista_partidos[pos].id){
                pos++;
            }
            return this.lista_partidos[pos];
        },
        gravarCandidato() {
            let candidato = {
                id: this.id,
                numero: this.numero,
                nome: this.nome,
                partido: this.buscarPartido()
            }
            let myMethod = 'post';
            let myUrl = 'http://localhost:8080/apis/candidato/incluir';
            if (this.alterar) {
                myUrl = 'http://localhost:8080/apis/candidato/alterar';
                myMethod = 'put';
            }
            axios({
                method: myMethod,
                url: myUrl,
                timeout: 4000, // 4 segundos timeout
                data: candidato
            })
            .then(() => setTimeout(() => {
                if (this.novo_cargo.length > 0) {
                    axios.get(`http://localhost:8080/apis/candidato/buscar-nome?nome=${this.nome}`)
                    .then(response => {
                        candidato = response.data;
                    })
                    .then(   () => {
                        if (this.novo_cargo.length > 0) {
                            axios({
                                method: 'post',
                                url: 'http://localhost:8080/apis/cargo/incluir',

                                data: {
                                    id: 0,
                                    descricao: this.novo_cargo,
                                    candidato: candidato
                                }
                            })
                                .then(response => {
                                        cadidato_gerenciar.carregarTabela();
                                        this.novo_cargo = '';
                                    }
                                )
                                .catch(error => console.error('Inserir cargo ERROR - ' + error))
                        }
                    })
                }
            }, 500))
            .then( response => {
                    myModal.toggle();
                    cadidato_gerenciar.carregarTabela();
                }
            )
            .catch(error => console.error('Inserir candidato ERROR - ' + error))
        },
        partidoSelecionado(event) {
            this.partido_id = event.target.value;
            console.log(this.partido_id);
        },
        mudarValorBotao(valor) {
            this.valorBotao = valor
            this.novo_cargo = ''
        },
        apagarForm() {
            this.id = '0',
            this.nome = '',
            this.numero = '',
            this.cargos = '',
            this.partido_id = '',
            this.novo_cargo = ''
        }
    },
    template:
        `        
        <div class="modal" id="myModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-header">
                    <h4 class="modal-title">Candidato</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body">
                    <div class="container mt-1">
                        <form>                        
                            <div class="mb-3">
                                <label for="nome">Nome:</label>
                                <input type="text" class="form-control" id="nome" v-model="nome" placeholder="Informe um nome do candidato" >
                            </div>   
                            
                            <div class="mb-3 mt-3">
                                <label for="id">Numero:</label>
                                <input type="number" class="form-control" id="id" v-model="numero">
                            </div>
                            
                            <div class="input-group mb-3 d-flex">
                                <label class="input-group-text" for="inputGroupSelect01">
                                    Partidos
                                </label> 
                                <select class="form-select" id="inputGroupSelect01" @change="partidoSelecionado" v-model="partido_id">
                                    <option v-for="partido in lista_partidos" :value="partido.id">
                                        {{partido.nome}}
                                    </option>
                                </select>
                            </div> 
                                    
                            <div class="mb-3 mt-3">
                                <label for="id">Cargos:</label>
                                <p class="form-control mb-1" v-for="cargo in cargos">
                                    {{cargo.descricao}}
                                </p>                                
                                <input type="text" class="form-control" v-model="novo_cargo" placeholder="Informe novo cargo">
                            </div>      
                        </form>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button @click="gravarCandidato" class="btn btn-success">{{this.valorBotao}}</button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                </div>

            </div>
        </div>
    </div>
    `
}

const v_cadidato = {
    data() {
        return {
            candidatos: '',
            filtro: '',
            votos: ''
        }
    },
    mounted() {
        this.carregarTabela();
        //this.carregarVotos();
    },
    methods: {
        carregarTabela() {
            axios
                .get(`http://localhost:8080/apis/candidato/buscar-todos?filtro=${this.filtro}`)
                .then(response => {
                    this.candidatos = response.data;
                    this.candidatos.sort(compare)
                })
                .catch(err => { this.info = err })
        },
        carregarVotos(id){

            axios.get(`http://localhost:8080/apis/candidate/buscar-todos?filtro=${id}`)
            .then(response => {
            this.votos = response.data;
            }).catch(err => {this.info = err})
        },
        cadastrar() {
            this.apagarForm();
            cadidato_form.alterar = false;
            cadidato_form.mudarValorBotao('Cadastrar');
        },
        apagar(id) {
            if(confirm("Deseja Excluir?")) {
                axios({
                    method: 'delete',
                    url: `http://localhost:8080/apis/candidato/${id}`,
                    timeout: 4000, // 4 segundos timeout
                })
                .then(response => cadidato_gerenciar.carregarTabela())
                .catch(error => console.error('timeout excedido'))
            }

        },
        alterar(id) {
            cadidato_form.mudarValorBotao('Alterar')
            myModal.toggle();
            axios
            .get(`http://localhost:8080/apis/candidato/buscar-um?id=${id}`)
            .then(response => {
                let candidato = response.data;
                cadidato_form.id = candidato.id;
                cadidato_form.nome = candidato.nome;
                cadidato_form.numero = candidato.numero;
                cadidato_form.partido_id = candidato.partido.id;
                cadidato_form.cargos = candidato.cargos;
                cadidato_form.votos = candidato.votos;
                cadidato_form.alterar = true;
            })
            .catch(err => { this.info = err })
        },
        apagarForm() {
            cadidato_form.apagarForm()
        }
    },
    template:
        `
        <h2 class="text-3xl text-center font-bold">Gerenciar Candidatos</h2>      
                
        <div class="my-3 d-flex flex-column flex-sm-row align-content-center justify-content-center">
            <button type="button" @click="cadastrar" class="me-2 mt-1 btn btn-success" data-bs-toggle="modal" data-bs-target="#myModal">
                Novo Candidato
            </button>
            <input type="text" @keyup="carregarTabela" class="form-control mt-1" id="filtro" v-model="filtro" placeholder="informe o filtro" >
        </div>
        
        <div class="overflow-auto">
            <table class="table table-hover table-bordered">
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>Numero</th>
                  <th>Partido</th>
                  <th>Cargos</th>
                  <th>Votos Recebidos</th>
                  <th>Alterar</th>
                  <th>Excluir</th>
                </tr>
              </thead>
              <tbody>
              <tr v-for="c in this.candidatos">              
                  <td>{{c.nome}}</td>
                  <td>{{c.numero}}</td>
                  <td>{{c.partido.nome}}</td>
                  <td>
                    <ul v-for="cargo in c.cargos">
                       <li>{{cargo.descricao}}</li>
                    </ul>
                  </td>
                  <td>
                    <div @ v-for="v in this.votos" class="d-flex">
                       <p><span class="fw-bold">{{v.eleicao}}</span>: {{v.total}}</p>
                    </div>
                  </td>              
                  <td @click="alterar(c.id)">
                    <div class="d-flex justify-content-center">
                        <span class="bi bi-pencil"></span>
                    </div>
                    
                  </td>
                  <td @click="apagar(c.id)">
                    <div class="d-flex justify-content-center">
                        <span class="bi bi-trash"></span>
                    </div>                    
                  </td>
              </tr>
              </tbody>
            </table> 
        </div>
    `
};


const cadidato_gerenciar =  Vue.createApp(v_cadidato).mount('#app');
const cadidato_form = Vue.createApp(vform_candidato).mount('#appmodal')

var myModal = new bootstrap.Modal(document.getElementById('myModal'), {
    keyboard: false
})
