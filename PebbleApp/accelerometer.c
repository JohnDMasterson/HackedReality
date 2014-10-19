#include <pebble.h>
  
#define ACCEL_STEP_MS 25
#define BATCH_SIZE 10

Window *window;
static AppTimer *timer;
	
enum {
	X_KEY = 0,	
	Y_KEY = 1,
  Z_KEY = 2
};

void send_message(void){
  AccelData accel = (AccelData) { .x = 0, .y = 0, .z = 0 };
  DictionaryIterator *iter;
	app_message_outbox_begin(&iter);
  for(int i=0; i<BATCH_SIZE; i++){
    accel_service_peek(&accel);
	  dict_write_int16(iter, 3*i+X_KEY, accel.x);
	  dict_write_int16(iter, 3*i+Y_KEY, accel.y);
	  dict_write_int16(iter, 3*i+Z_KEY, accel.z);
  }
	dict_write_end(iter);
  app_message_outbox_send();
}

// void send_message(AccelData* accel){
// 	DictionaryIterator *iter;
// 	app_message_outbox_begin(&iter);
// 	dict_write_int16(iter, X_KEY, accel->x);
// 	dict_write_int16(iter, Y_KEY, accel->y);
// 	dict_write_int16(iter, Z_KEY, accel->z);
// 	dict_write_end(iter);
//   app_message_outbox_send();
// }

static void send_accel_data(void) {
  //AccelData accel = (AccelData) { .x = 0, .y = 0, .z = 0 };
  //accel_service_peek(&accel);
  //send_message(&accel);
  send_message();
}

static void timer_callback(void *data) {
  send_accel_data();
}

static void in_received_handler(DictionaryIterator *received, void *context) {
}

static void in_dropped_handler(AppMessageResult reason, void *context) {	
}

static void out_failed_handler(DictionaryIterator *failed, AppMessageResult reason, void *context) {
  timer = app_timer_register(ACCEL_STEP_MS, timer_callback, NULL);
}

static void out_sent_handler(DictionaryIterator *sent, void *context) {
  send_accel_data();
}

void init(void) {
	window = window_create();
	window_stack_push(window, true);
  app_comm_set_sniff_interval(SNIFF_INTERVAL_REDUCED);
	app_message_register_inbox_received(in_received_handler); 
	app_message_register_inbox_dropped(in_dropped_handler); 
	app_message_register_outbox_failed(out_failed_handler);
  app_message_register_outbox_sent(out_sent_handler);
	app_message_open(app_message_inbox_size_maximum(), app_message_outbox_size_maximum());
  accel_data_service_subscribe(0, NULL);
	accel_service_set_sampling_rate(ACCEL_SAMPLING_100HZ);
  send_accel_data();
}

void deinit(void) {
	app_message_deregister_callbacks();
	window_destroy(window);
}

int main( void ) {
	init();
	app_event_loop();
	deinit();
}