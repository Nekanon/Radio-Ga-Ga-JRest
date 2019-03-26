function getIndex(list, id) {
    for(var i = 0; i < list.length; i++ ) {
        if(list[i].id === id) {
            return i;
        }
    }
    return -1;
}

//template for HTTP requests
var messageApi = Vue.resource('/message{/id}');

//new form-base for message-row with buttons of edit
Vue.component('message-form', {
        props: ['messages', 'messageAttr'],
        data: function() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            messageAttr: function(newVal, oldVal) {
                this.text = newVal.text;
                this.id = newVal.id;
            }
        },
        template:
            '<div>' +
                '<input type="text" placeholder="Write something" v-model="text" />' +
                '<input type="button" value="Save" v-on:click="save" />' +
            '</div>',
        methods: {
            save: function() {
                var message = {text: this.text };

                if(this.id) {
                    messageApi.update({id: this.id}, message).then(result =>
                        result.json().then( data => {
                            var index = getIndex(this.messages, data.id);
                            this.messages.splice(index, 1, data);
                            this.text = '';
                            this.id = '';
                        })
                    )
                } else {
                    messageApi.save({}, message).then( result =>
                        result.json().then(data => {
                            this.messages.push(data);
                            this.text = '';
                        })
                    )
                }
            }
        }
    });

//create new component "row" for reading and editing
Vue.component('message-row', {
    props: ['message', 'editMethod', 'messages'],
    template: '<div>' + '' +
        '<i>({{message.id}})</i> - {{message.text}}' +
        '<span id="message-row">' +
            '<input type="button" value="Edit" v-on:click="edit" />' +
            '<input type="button" value="Delete" v-on:click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.message);
        },
        del: function() {
            messageApi.remove({id: this.message.id}).then( result => {
                    if(result.ok) {
                        this.messages.splice(this.messages.indexOf(this.message), 1)
                    }
                }

            )
        }
    }
});

//create new component "list" reading and editing
Vue.component('messages-list', {
    props: ['messages'],
    data: function() {
        return {
            message: null
        }
    },
    template:
        '<div id="message-list">' +
            '<message-form v-bind:messages="messages" v-bind:messageAttr="message" /> ' +
            '<message-row v-for="message in messages" :key="message.id" :message="message" ' +
            'v-bind:editMethod="editMethod" v-bind:messages="messages" />' +
        '</div>',
    created: function() {
        messageApi.get().then(result =>
            result.json().then(data =>
                data.forEach(message => this.messages.push(message))
            )
        )
    },
    methods: {
        editMethod: function(message) {
            this.message = message;
        }
    }
});

//create tag-application
var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: []
    }
});