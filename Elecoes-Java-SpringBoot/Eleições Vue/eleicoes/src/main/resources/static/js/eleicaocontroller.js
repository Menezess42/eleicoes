// Função Ordenar
function compare(a, b) {
    if(a.ano > b.ano)
        return -1;
    if(a.ano < b.ano)
        return 1;
    return 0;
}

const vform_eleicao = {
    data() {
        return {
            id: '0',
            tipo: '',
            ano: '',

            lista_candidatos: '',
            eleicao_candidatos: [],
            eleicao: '',
            valorBotao: '',
            alterar: false
        }
    },
    mounted() {
        axios.get('http://localhost:8080/apis/candidato/buscar-todos?filtro')
        .then(response => {
            this.lista_candidatos = response.data;
        })
    },
    methods: {
        buscarCandidato(id) {
            let candidato = "";
            axios.get(`http://localhost:8080/apis/candidato/buscar-um?id=${id}`)
                .then(response => {
                    candidato = response.data;
                })
            return candidato;
        },
        gravar() {
            let elementos = document.getElementsByName("opCandidatos");
            console.log(elementos)
            for(let i=0; i< elementos.length; i++) {
                if(elementos[i].checked) {
                    axios.get(`http://localhost:8080/apis/candidato/buscar-um?id=${elementos[i].value}`)
                    .then(response => {
                        this.eleicao_candidatos.push(response.data);
                    })
                }
            }
            setTimeout(() => {
                let myMethod = 'post';
                let myUrl = 'http://localhost:8080/apis/eleicao/incluir';
                if (this.alterar) {
                    myUrl = 'http://localhost:8080/apis/eleicao/alterar';
                    myMethod = 'put';
                }
                axios({
                    method: myMethod,
                    url: myUrl,
                    timeout: 4000, // 4 segundos timeout
                    data: {
                        id: this.id,
                        tipo: this.tipo,
                        ano: this.ano
                    }
                })
                .catch(error => console.error('Error Eleicao - ' + error))

                setTimeout(() => {
                    axios.get(`http://localhost:8080/apis/eleicao/buscar-nome?nome=${this.tipo}`)
                    .then(response => {
                        this.eleicao = response.data;
                    })
                    .then( () => {
                        let voto_eleicao_candidato = []
                        // Inserir na Tabela Voto
                        this.eleicao_candidatos.forEach(candidato => {
                            voto_eleicao_candidato.push({
                                vot_id: 0,
                                total: 0,
                                candidato: {
                                    id: candidato.id,
                                    numero: candidato.numero,
                                    nome: candidato.nome,
                                    partido: candidato.partido,
                                    cargos: candidato.cargos
                                },
                                eleicao: this.eleicao
                            })
                        })
                        setTimeout(() => {
                            axios({
                                method: 'post',
                                url: 'http://localhost:8080/apis/votos/incluir-varios',
                                timeout: 4000, // 4 segundos timeout
                                data: voto_eleicao_candidato
                            })
                        }, 50)
                    })
                    .then( response => {
                            myModal.toggle();
                            eleicao_gerenciar.carregarTabela();
                        }
                    )
                    .catch(error => console.error('Error Voto - ' + error))
                }, 150)
            }, 50)
        },
        mudarValorBotao(valor) {
            this.tipo = '';
            this.ano = '';
            this.valorBotao = valor;
        },
        apagarForm() {
            this.id = '0';
            this.tipo = '';
            this.ano = '';
        }
    },
    template:
        `        
        <div class="modal" id="myModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-header">
                    <h4 class="modal-title">Eleição</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body">
                    <div class="container mt-1">
                        <form>
                            <div class="mb-3 mt-3">
                                <label for="id">ID:</label>
                                <input type="id" class="form-control" id="id" v-model="id" readonly>
                            </div>
                        
                            <div class="mb-3">
                                <label for="tipo">Tipo:</label>
                                <input type="text" class="form-control" id="tipo" v-model="tipo" placeholder="Ex: prefeito; representante escolha;" >
                            </div> 
                            
                            <div class="mb-3">
                                <label for="ano">Ano:</label>
                                <input type="number" class="form-control" id="ano" v-model="ano" placeholder="Informe o ano da eleição" >
                            </div>  
                            
                            <div class="container">
                                <div style="
                                    display: grid;
                                    grid-template-columns: auto auto auto;
                                    justify-content: space-between;
                                    gap: 10px;
                                    padding: 10px;
                                ">
                                  <label  v-for="c in lista_candidatos" >
                                    <input type="checkbox" :value="c.id" name="opCandidatos">
                                    {{c.nome}}
                                  </label>
                                </div>                                
                            </div>
                          
                                    
                        </form>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button @click="gravar" class="btn btn-success">{{this.valorBotao}}</button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                </div>

            </div>
        </div>
    </div>
    `
}

const v_eleicao = {
    data() {
        return {
            eleicao: '',
            filtro: ''
        }
    car,
    mounted() {
        this.carregarTabela();
    },
    methods: {
        carregarTabela() {
            axios
                .get(`http://localhost:8080/apis/eleicao/buscar-todos?filtro=${this.filtro}`)
                .then(response => {
                    this.eleicao = response.data;
                    this.eleicao.sort(compare);
                })
                .catch(err => { this.info = err })
        },
        cadastrar() {
            this.apagarForm();
            eleicao_form.alterar = false;
            eleicao_form.mudarValorBotao('Cadastrar');
        },
        apagar(id) {
            if(confirm("Deseja Excluir?")) {
                axios({
                    method: 'delete',
                    url: `http://localhost:8080/apis/eleicao/${id}`,
                    timeout: 4000, // 4 segundos timeout
                })
                .then( response => eleicao_gerenciar.carregarTabela())
                .catch(error => console.error('timeout excedido'))
            }
        },
        alterar(id) {
            eleicao_form.mudarValorBotao('Alterar')
            myModal.toggle();
            axios
            .get(`http://localhost:8080/apis/eleicao/buscar-um?id=${id}`)
            .then(response => {
                let eleicao = response.data;
                eleicao_form.id = eleicao.id;
                eleicao_form.tipo = eleicao.tipo;
                eleicao_form.ano = eleicao.ano;
                eleicao_form.alterar = true;
            })
            .catch(err => { this.info = err })
        },
        apagarForm() {
            eleicao_form.apagarForm()
        }
    },
    template:
        `
        <h2 class="text-3xl text-center font-bold">Gerenciar Eleição</h2>      
                
        <div class="my-3 d-flex flex-column flex-sm-row align-content-center justify-content-center">
            <button type="button" @click="cadastrar" class="me-2 mt-1 btn btn-success" data-bs-toggle="modal" data-bs-target="#myModal">
                Nova Eleição
            </button>
            <input type="text" @keyup="carregarTabela" class="form-control mt-1" id="filtro" v-model="filtro" placeholder="informe o filtro" >
        </div>
        
        <table class="table table-hover table-bordered">
          <thead>
            <tr>
              <th>Tipo</th>
              <th>Ano</th>
              <th>Alterar</th>
              <th>Excluir</th>
            </tr>
          </thead>
          <tbody>
          <tr v-for="e in this.eleicao"> 
              <td>{{e.tipo}}</td>   
              <td>{{e.ano}}</td>    
              <td @click="alterar(e.id)">
                <div class="d-flex justify-content-center">
                    <span class="bi bi-pencil"></span>
                </div>
                
              </td>
              <td @click="apagar(e.id)">
                <div class="d-flex justify-content-center">
                    <span class="bi bi-trash"></span>
                </div>                    
              </td>
          </tr>
          </tbody>
        </table> 
    `
};

const eleicao_gerenciar =  Vue.createApp(v_eleicao).mount('#app');
const eleicao_form = Vue.createApp(vform_eleicao).mount('#appmodal')

var myModal = new bootstrap.Modal(document.getElementById('myModal'), {
    keyboard: false
})