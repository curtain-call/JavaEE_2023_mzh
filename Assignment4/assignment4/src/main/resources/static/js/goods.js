 var vue=new Vue({
        el: '#goods',
        data:{
            goods:[],//查询结果
            currentGoods:{},//当前编辑的商品
            id:"",
            addMode:false,
            modifyMode:false
           
        },
        methods: {
            query: function(){
                var path='/goods';
                axios.get(path+'/query')
                .then(response=>this.goods=response.data)
                .catch(e=>this.$message.error(e.response.data))
            },
            addGoods:function(){
                var path='/goods/add';
                axios.post(path,this.currentGoods)
                .then(response=>this.query())
                .catch(e=>this.$message.error(e.response.data))
                
                this.addMode=false;
                this.currentGoods.id="";
                this.currentGoods.name="";
                this.currentGoods.price="";
               
            },
            deleteGoods:function(){
                var path='/goods/delete/';
                axios.delete(path+this.id)
                .then(response=>this.query())
                .catch(e=>this.$message.error(e.response.data))
               
            },
            modifyGoods:function(){
                var path='/goods/modify/';
                axios.put(path+this.currentGoods.id,this.currentGoods)
                .then(response=>this.query())
                .catch(e=>this.$message.error(e.response.data))
                this.currentGoods.id="";
                this.currentGoods.name="";
                this.currentGoods.price="";
                this.modifyMode=false;
            },
            queryById:function(){
                var path='/goods/';
                axios.get(path+this.id)
                .then(response=>this.goods=[response.data])
                .catch(e=>this.$message.error(e.response.data))
            },
            showAdd:function(){
                this.addMode=true;
            },
            showModify:function(){
                this.modifyMode=true;
            }
           
           
        }
    })
    vue.query();