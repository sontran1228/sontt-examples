$(function() {
	// Todo Model
	var Todo = Backbone.Model.extend({

		defaults : {
			title : "empty todo...",
			done : false
		},

		initialize : function() {
			if (!this.get("title")) {
				this.set({
					"title" : this.defaults().title
				});
			}
		},

		toggle : function() {
			this.save({
				done : !this.get("done")
			});
		},
		
		editTitle : function(title) {
			this.save({
				title : title
			});
		}
	});

	// Todo Collection
	var TodoList = Backbone.Collection.extend({

		model : Todo,

		done : function() {
			return this.filter(function(todo) {
				return todo.get('done');
			});
		},

		remaining : function() {
			return this.without.apply(this, this.done());
		},

		clearCompleted : function() {
			this.remove(this.done());
			var options = {};
			this.sync("deleteCompleted", this, options);
		},
		
		toggleAllComplete : function(_done) {
			this.each(function(todo) {
				todo.set({
					'done' : _done
				});
			});
			var options = {};
			options.data = "done=" + _done;
			this.sync("completeAll", this, options)
		},
		
		addTodo : function(title) {
			this.create({
				title : title 
			});
		}

	});

	// Todo Item View
	var TodoView = Backbone.View.extend({

		tagName : "li",

		template : _.template($('#item-template').html()),

		events : {
			"click .toggle" : "toggleDone",
			"dblclick .view" : "edit",
			"click a.destroy" : "clear",
			"keypress .edit" : "updateOnEnter",
			"blur .edit" : "finishEdit"
		},

		initialize : function() {
			this.listenTo(this.model, 'change', this.render);
			this.listenTo(this.model, 'destroy', this.remove);
			this.listenTo(this.model, 'remove', this.remove);
		},

		render : function() {
			this.$el.html(this.template(this.model.toJSON()));
			this.$el.toggleClass('done', this.model.get('done'));
			this.input = this.$('.edit');
			return this;
		},

		toggleDone : function() {
			this.model.toggle();
		},

		edit : function() {
			this.$el.addClass("editing");
			this.input.focus();
		},

		finishEdit : function() {
			var value = this.input.val();
			if (!value) {
				this.clear();
			} else {
				this.model.editTitle(value);
				this.$el.removeClass("editing");
			}
		},

		updateOnEnter : function(e) {
			if (e.keyCode == 13)
				this.finishEdit();
		},

		clear : function() {
			this.model.destroy();
		}

	});

	// The Application
	var AppView = Backbone.View.extend({

		el : $("#todoapp"),

		statsTemplate : _.template($('#stats-template').html()),

		events : {
			"keypress #new-todo" : "createOnEnter",
			"click #clear-completed" : "clearCompleted",
			"click #toggle-all" : "toggleAllComplete"
		},

		initialize : function() {

			this.input = this.$("#new-todo");
			this.allCheckbox = this.$("#toggle-all")[0];

			this.listenTo(Todos, 'add', this.addOne);
			this.listenTo(Todos, 'reset', this.addAll);
			this.listenTo(Todos, 'all', this.render);

			this.footer = this.$('footer');
			this.main = $('#main');

			Todos.fetch();
		},

		render : function() {
			var done = Todos.done().length;
			var remaining = Todos.remaining().length;

			if (Todos.length) {
				this.main.show();
				this.footer.show();
				this.footer.html(this.statsTemplate({
					done : done,
					remaining : remaining
				}));
			} else {
				this.main.hide();
				this.footer.hide();
			}

			this.allCheckbox.checked = !remaining;
		},

		addOne : function(todo) {
			var view = new TodoView({
				model : todo
			});
			this.$("#todo-list").append(view.render().el);
		},

		addAll : function() {
			Todos.each(this.addOne, this);
		},

		createOnEnter : function(e) {
			if (e.keyCode != 13)
				return;
			if (!this.input.val())
				return;

			Todos.addTodo(this.input.val()) 
			this.input.val('');
		},

		clearCompleted : function() {
			Todos.clearCompleted();
		},

		toggleAllComplete : function() {
			var done = this.allCheckbox.checked;
			Todos.toggleAllComplete(done);
		}

	});
	
	var Todos = new TodoList;
	var App = new AppView;
});