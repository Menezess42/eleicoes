// Função Ordenar
function compare(a, b) {
    if(a.descricao.toLowerCase() < b.descricao.toLowerCase())
        return -1;
    if(a.descricao.toLowerCase() > b.descricao.toLowerCase())
        return 1;
    return 0;
}

const vform_cargo = {
    data() {
        return {
            id: '0',
            descricao: '',

            valorBotao: '',
            alterar: false,
        }
    },
    methods: {
        gravar() {
            let myMethod = 'post';
            let myUrl = 'http://localhost:8080/apis/cargo/incluir';
            if(this.alterar) {
                myUrl = 'http://localhost:8080/apis/cargo/alterar';
                myMethod = 'put';
            }
            axios({
                method: myMethod,
                url: myUrl,
                timeout: 4000, // 4 segundos timeout
                data: {
                    id: this.id,
                    descricao: this.descricao
                }
            })
            .then( response => {
                    myModal.toggle();
                    cargo_gerenciar.carregarTabela();
                }
            )
            .catch(error => console.error('timeout excedido'))
        },
        mudarValorBotao(valor) {
            this.valorBotao = valor
        },
        apagarForm() {
            this.id = '0';
            this.descricao = '';
        }
    },
    template:
        `        
        <div class="modal" id="myModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-header">
                    <h4 class="modal-title">Cargo</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body">
                    <div class="container mt-1">
                        <form>                        
                            <div class="mb-3">
                                <label for="descricao">Descrição:</label>
                                <input type="text" class="form-control" id="descricao" v-model="descricao" placeholder="Informe o nome do cargo" >
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

const v_cargo = {
    data() {
        return {
            cargo: '',
            filtro: ''
        }
    },
    mounted() {
        this.carregarTabela();
    },
    methods: {
        carregarTabela() {
            axios
                .get(`http://localhost:8080/apis/cargo/busca-todos?filtro=${this.filtro}`)
                .then(response => {
                    this.cargo = response.data;
                    this.cargo.sort(compare);
                })
                .catch(err => { this.info = err })
        },
        gravar() {
            this.apagarForm();
            cargo_form.alterar = false;
            cargo_form.mudarValorBotao('Cadastrar')
        },
        apagar(id) {
            if(confirm("Deseja Excluir?")) {
                axios({
                    method: 'delete',
                    url: `http://localhost:8080/apis/cargo/${id}`,
                    timeout: 4000, // 4 segundos timeout
                })
                    .then(response => cargo_gerenciar.carregarTabela())
                    .catch(error => console.error('timeout excedido'))

            }

        },
        alterar(id) {
            cargo_form.mudarValorBotao('Alterar')
            myModal.toggle();
            axios
                .get(`http://localhost:8080/apis/cargo/buscar-um?id=${id}`)
                .then(response => {
                    let cargo = response.data;
                    cargo_form.id = cargo.id;
                    cargo_form.descricao = cargo.descricao;
                    cargo_form.alterar = true;
                })
                .catch(err => { this.info = err })
        },
        apagarForm() {
            cargo_form.apagarForm()
        }
    },
    template:
        `
        <h2 class="text-3xl text-center font-bold">Gerenciar Cargos</h2>      
                
        <div class="my-3 d-flex flex-column flex-sm-row align-content-center justify-content-center">
            <button type="button" @click="gravar" class="me-2 mt-1 btn btn-success" data-bs-toggle="modal" data-bs-target="#myModal">
                Novo Cargo
            </button>
            <input type="text" @keyup="carregarTabela" class="form-control mt-1" id="filtro" v-model="filtro" placeholder="informe o filtro" >
        </div>
        
        <table class="table table-hover table-bordered">
          <thead>
            <tr>
              <th>Descrição</th>
              <th>Alterar</th>
              <th>Excluir</th>
            </tr>
          </thead>
          <tbody>
          <tr v-for="c in this.cargo">   
              <td>{{c.descricao}}</td>             
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
    `
};

const cargo_gerenciar =  Vue.createApp(v_cargo).mount('#app');
const cargo_form = Vue.createApp(vform_cargo).mount('#appmodal')

var myModal = new bootstrap.Modal(document.getElementById('myModal'), {
    keyboard: false
})