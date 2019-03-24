//template for HTTP requests
var messageApi = Vue.resource('/message{/id}');

//create new component "row" for output
Vue.component('message-row', {
    props: ['message'],
    template: '<div><i>({{message.id}})</i> - {{message.text}}</div>'
});

//create new component "list" for output
Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div>' +
            '<message-row v-for="message in messages" :key="message.id" :message="message"/>' +
        '</div>',
    created: function() {
        messageApi.get().then(result =>
            result.json().then(data =>
                data.forEach(message => this.messages.push(message))
            )
        )
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
