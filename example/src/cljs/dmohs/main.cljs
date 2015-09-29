(ns dmohs.main
  (:require
   [dmohs.react :as react]
   ))


(defn rlog [& args]
  (let [arr (array)]
    (doseq [x args] (.push arr x))
    (js/console.log.apply js/console arr))
  (last args))


(defn jslog [& args]
  (apply rlog (map clj->js args))
  (last args))


(defn cljslog [& args]
  (apply rlog (map pr-str args))
  (last args))


(react/defc App
  {:render
   (fn []
     [:div {} "Hello World!"])})


(defn- render-without-init [element]
  (react/render (react/create-element App) element nil goog.DEBUG))


(defonce dev-element (atom nil))


(defn ^:export render [element]
  (when goog.DEBUG
    (reset! dev-element element))
  (render-without-init element))


(defn dev-reload [figwheel-data]
  (render-without-init @dev-element))


#_(react/defc App
    {:get-initial-state
     (fn []
       {:click-count 0})
     :render
     (fn [{:keys [state]}]
       [:div {:style {:fontSize "x-large"}}
        (if (:items @state)
          [:div {}
           (map (fn [i]
                  [:div {} (get i "name")])
                (:items @state))]
          [:div {} "Loading..."])])
     :component-did-mount
     (fn [{:keys [state]}]
       (let [xhr (js/XMLHttpRequest.)]
         (.open xhr "GET" "http://dhost:8088/repos/dmohs/react-cljs/contents")
         (.setRequestHeader xhr "X-Hi-There" "How are you!")
         (.addEventListener
          xhr "loadend"
          (fn [] (swap! state assoc
                        :items
                        (js->clj (js/JSON.parse (.-responseText xhr))))))
         (.send xhr)
         (rlog xhr)))})
