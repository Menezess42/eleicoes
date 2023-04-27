const grid_votacao = {
    data() {
         return {
             eleicao: '', filtro: '',votos: ''
            } 
        },
    mounted() {
        this.carregarTabela();
    },
    methods: {
        carregarTabela() {
            // this.partidos = [{ "id": 1, "nome": "PPL" }];
            axios
            .get(`http://localhost:8080/apis/eleicao/buscar-todos?filtro=${this.filtro}`)
            .then(response => { this.eleicao = response.data })
            .then(() => this.eleicao.sort(compare))
            .catch(err => { this.info = err })
        },
        candidatos(filter)
        {
            axios
            .get(`http://localhost:8080/apis/votos/buscar-eleicao?filtro=${filter}`)
            .then(response => { this.votos = response.data })
            .then(() => this.votos.sort(compare))
            .catch(err => { this.info = err })
            console.log(this.votos)
        }
    },
    template:
    `
    <div v-for="e in this.eleicao">
        <div class="container1" >
            <button @click="candidatos(e.id)">
                <div class="grid">
                <div>
                        <img src="./img/ELEI20.jpg" alt="ELEI20" class="photo"/>
                        </div>
                        <div>
                        <h2 class="titulo">{{e.tipo}}</h2>
                        <h2 class="titulo">{{e.ano}}</h2>
                        </div>
                </div>
            </button>
        </div>
    </div>
    `
};

const partido_gerenciar =  Vue.createApp(grid_votacao).mount('#app');